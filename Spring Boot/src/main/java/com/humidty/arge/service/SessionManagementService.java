package com.humidty.arge.service;

import com.humidty.arge.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

import org.json.*;

@Service
public class SessionManagementService {
    private final Map<String, WebSocketSession> deviceSessions;


    @Autowired
    public SessionManagementService(Map<String, WebSocketSession> deviceSessions) {
        this.deviceSessions = deviceSessions;

    }

    public WebSocketSession getSessionById(String id) {
        return deviceSessions.get(id);
    }

    public void registerSession(String deviceID, WebSocketSession session, TextMessage lastStatus) throws IOException {
        // Get the deviceID of the device (for this code to work properly,
        // the first message from the device must contain its deviceID)

        deviceSessions.put(deviceID, session);

        System.out.println("Device Connection established " + deviceID);

        session.sendMessage(lastStatus);

    }

    // Diğer oturum yönetimi metodları buraya gelebilir
}