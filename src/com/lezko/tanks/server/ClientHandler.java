package com.lezko.tanks.server;

import com.lezko.simplejson.ArrObj;
import com.lezko.simplejson.MapObj;
import com.lezko.simplejson.Obj;
import com.lezko.tanks.net.UDPSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClientHandler implements Runnable {

    private final UUID id = UUID.randomUUID();
    private final Map<UUID, GameSession> sessions;

    private UDPSender sender;

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClientHandler(Socket socket, Map<UUID, GameSession> sessions) throws IOException {
        this.socket = socket;
        this.sessions = sessions;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        Obj res = new MapObj();
        res.put("status", "ok");
        res.put("client_id", id.toString());
        sendTCP(res.toString());

        new Thread(this).start();
    }

    public void sendTCP(String s) {
        out.println(s);
    }

    public void sendUDP(String s) throws IOException {
        sender.send(s);
    }

    public UUID getId() {
        return id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Obj req = Obj.fromString(in.readLine());
                switch (req.get("type").val()) {
                    case "join" -> {
                        UUID sessionId = UUID.fromString(req.get("session_id").val());
                        try {
                            sessions.get(sessionId).addClient(this);
                            Obj res = new MapObj();
                            res.put("status", "ok");
                            sendTCP(res.toString());
                        } catch (GameSession.LimitExceedException e) {
                            Obj res = new MapObj();
                            res.put("status", "error");
                            res.put("message", e.getMessage());
                            sendUDP(res.toString());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    case "establish_udp" -> {
                        synchronized (this) {
                            try {
                                this.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        Obj res = new MapObj();
                        res.put("status", "ok");
                        sendTCP(res.toString());
                    }
                    case "create" -> {
                        // todo session limit exceed
                        GameSession newSession = new GameSession();
                        sessions.put(newSession.getId(), newSession);
                        new Thread(newSession).start();

                        Obj res = new MapObj();

                        res.put("status", "ok");
                        res.put("id", newSession.getId().toString());
                        sendTCP(res.toString());
                        System.out.println("created session " + newSession.getId());
                    }
                    case "exit" -> {
                        UUID sessionId = UUID.fromString(req.get("session_id").val());
                        UUID clientId = UUID.fromString(req.get("client_id").val());
                        sessions.get(sessionId).removeClient(clientId);
                        Obj res = new MapObj();
                        res.put("status", "ok");
                        sendTCP(res.toString());
                    }
                    case "sessions" -> {
                        Obj res = new MapObj();
                        Obj sessionsArr = new ArrObj();
                        for (GameSession session : sessions.values()) {
                            Obj sessionObj = new MapObj();
                            sessionObj.put("id", session.getId().toString());
                            sessionObj.put("players_count", String.valueOf(session.getPlayersCount()));

                            sessionsArr.append(sessionObj);
                        }
                        res.put("sessions", sessionsArr);
                        res.put("status", "ok");
                        sendTCP(res.toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean UDPReady() {
        return sender != null;
    }

    public void setSender(UDPSender sender) {
        this.sender = sender;
    }
}
