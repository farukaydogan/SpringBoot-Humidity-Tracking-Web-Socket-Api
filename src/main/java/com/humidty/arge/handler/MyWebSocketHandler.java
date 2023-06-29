package com.humidty.arge.handler;

;

import com.humidty.arge.service.SessionManagementService;
import com.humidty.arge.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.json.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MyWebSocketHandler extends TextWebSocketHandler  {
    // Kabul edilecek deviceID'lerin listesi
    private final List<String> acceptedDeviceIds = Arrays.asList("ARDUINO001", "ARDUINO002", "ARDUINO003");


    private final SessionManagementService sessionManagementService;

    @Autowired
    public MyWebSocketHandler(SessionManagementService sessionManagementService) {
        this.sessionManagementService = sessionManagementService;
    }

    @Autowired
    private WebSocketService webSocketService;
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException, IOException {
        // get payload from message
        String payload = message.getPayload();

        // Gelen payload mesajını bir JSON objesine dönüştürün
        JSONObject jsonObj = new JSONObject(payload);

        // JSON objesinden deviceID ve humidity değerlerini alın
        String deviceId = jsonObj.getString("deviceID");

        // Eğer deviceID, kabul edilenler listesinde değilse, bağlantıyı kapat
        if (!acceptedDeviceIds.contains(deviceId)) {
            return;
        }


        double humidity = jsonObj.getDouble("humidity");

        TextMessage responseMessage = webSocketService.handleHumidity(deviceId,humidity);

        session.sendMessage(responseMessage);
    }

    public final Map<String, WebSocketSession> deviceSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        // Cihazın deviceID'sini alın (bu kodun düzgün çalışabilmesi için,
        // cihazın ilk mesajının deviceID'sini içermesi gerekmektedir)
        String deviceId = (String) session.getAttributes().get("deviceID");

        sessionManagementService.registerSession(session);

        System.out.println("Connection established");
        session.sendMessage(new TextMessage("Welcome! Connection is successful."));
    }



}