package com.lezko.tanks.client;

import com.lezko.simplejson.MapObj;
import com.lezko.simplejson.Obj;
import com.lezko.tanks.net.UDPReceiver;
import com.lezko.tanks.net.UDPSender;
import com.lezko.tanks.ui.GameObjectEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.function.Consumer;

public class GameClient {

    private UUID clientId;
    private UUID serverSessionId;

    private final UDPSender sender;
    private final UDPReceiver receiver;

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private final Consumer<List<GameObjectEvent>> callback;

    private boolean listening = false;

    public GameClient(String address, int port, Consumer<List<GameObjectEvent>> callback) throws IOException {
        sender = new UDPSender(InetAddress.getByName(address), port);
        receiver = new UDPReceiver(sender.getSocket());

        socket = new Socket(address, 8888);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        Obj res = Obj.fromString(receiveUdp());
        if (res.get("status").val().equals("ok")) {
            clientId = UUID.fromString(res.get("client_id").val());
        } else {
            throw new RuntimeException("Unable to connect to server");
        }

        this.callback = callback;
    }

    public void sendUDP(String s) {
        out.println(s);
    }

    public String receiveUdp() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<UUID, Integer> getSessions() throws IOException {
        Obj req = new MapObj();
        req.put("type", "sessions");
        sendUDP(req.toString());

        Obj res = Obj.fromString(receiveUdp());
        Map<UUID, Integer> result = new HashMap<>();
        for (Map.Entry<String, Obj> entry : res.get("sessions")) {
            Obj sessionObj = entry.getValue();
            result.put(UUID.fromString(sessionObj.get("id").val()), Integer.parseInt(sessionObj.get("players_count").val()));
        }

        return result;
    }

    public void sendControlsState(String state) throws IOException {
        Obj req = new MapObj();
        req.put("type", "controls");
        req.put("session_id", serverSessionId.toString());
        req.put("client_id", clientId.toString());
        req.put("state", state);

        sender.send(req.toString());
    }

    public List<GameObjectEvent> parseData(String s) {
        Obj o = Obj.fromString(s);
        return o.toList().stream().map(GameObjectEvent::fromString).toList();
    }

    public void createSession() throws IOException {
        Obj req = new MapObj();
        req.put("type", "create");
        sendUDP(req.toString());

        Obj o = Obj.fromString(receiveUdp());
        if (!o.get("status").val().equals("ok")) {
            throw new RuntimeException("Failed to create new session");
        }
    }

    public void joinSession(UUID sessionId) throws IOException {
        serverSessionId = sessionId;
        Obj req = new MapObj();
        req.put("type", "join");
        req.put("session_id", sessionId.toString());
        sendUDP(req.toString());

        Obj res = Obj.fromString(receiveUdp());
        System.out.println(res);
        if (Objects.equals(res.get("status").val(), "ok")) {
            startListening();
        } else {
            throw new RuntimeException(res.get("message").val());
        }
    }

    public void exitSession() throws IOException {
        Obj req = new MapObj();
        req.put("type", "exit");
        req.put("session_id", serverSessionId.toString());
        req.put("client_id", clientId.toString());
        sendUDP(req.toString());
        stopListening();
    }

    public void startListening() {
        try {
            Obj req = new MapObj();
            req.put("type", "establish_udp");
            sendUDP(req.toString());

            Obj udpReq = new MapObj();
            udpReq.put("type", "hello_udp");
            udpReq.put("client_id", clientId.toString());

            sender.send(udpReq.toString());

            Obj res = Obj.fromString(receiveUdp());
            if (!res.get("status").val().equals("ok")) {
                throw new RuntimeException("Cannot establish udp connection");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        listening = true;
        Thread listeningThread = new Thread(() -> {
            try {
                while (true) {
                    if (!listening) {
                        break;
                    }

                    String str = receiver.getLine();
                    callback.accept(parseData(str));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        listeningThread.start();
    }

    public void stopListening() {
        listening = false;
    }
}
