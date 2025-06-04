package com.finalProject.SoftUniProject.webSocket;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SignalingHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws JSONException, IOException {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        System.out.println("Connected: " + sessionId);

        JSONObject json = new JSONObject();
        json.put("type", "id");
        json.put("id", sessionId);
        session.sendMessage(new TextMessage(json.toString()));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Expecting message in JSON: { "to": "targetSessionId", "type": "offer|answer|candidate", "data": {...} }
        JSONObject json = new JSONObject(message.getPayload());
        String to = json.getString("to");

        WebSocketSession targetSession = sessions.get(to);
        if (targetSession != null && targetSession.isOpen()) {
            targetSession.sendMessage(new TextMessage(json.toString()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
    }
}
