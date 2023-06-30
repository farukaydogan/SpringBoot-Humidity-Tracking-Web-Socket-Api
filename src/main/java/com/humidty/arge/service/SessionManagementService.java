package com.humidty.arge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

@Service
public class SessionManagementService {
    private final Map<String, WebSocketSession> deviceSessions;

    public void sendMessageToDevice(String deviceId, String message) throws IOException {
        WebSocketSession session = deviceSessions.get(deviceId);
        System.out.println(deviceSessions);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }

    @Autowired
    public SessionManagementService(Map<String, WebSocketSession> deviceSessions) {
        this.deviceSessions = deviceSessions;

    }

    public WebSocketSession getSessionById(String id) {
        return deviceSessions.get(id);
    }

    public void registerSession(String deviceID, WebSocketSession session) throws IOException {
        // Get the deviceID of the device (for this code to work properly,
        // the first message from the device must contain its deviceID)

        deviceSessions.put(deviceID, session);

        System.out.println("Connection established");
        session.sendMessage(new TextMessage("Welcome! Connection is successful."));

    }

    // Diğer oturum yönetimi metodları buraya gelebilir
}