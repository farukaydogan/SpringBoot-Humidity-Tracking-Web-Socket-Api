package com.humidty.arge.handler;

import com.humidty.arge.controller.WebSocketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.json.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    // Kabul edilecek deviceID'lerin listesi
    private final List<String> acceptedDeviceIds = Arrays.asList("ARDUINO001", "ARDUINO002", "ARDUINO003");
    private final WebSocketController webSocketController;

    @Autowired
    public MyWebSocketHandler(WebSocketController webSocketController) {
        this.webSocketController = webSocketController;
    }

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

        TextMessage responseMessage = webSocketController.handleHumidity(deviceId,humidity);

        session.sendMessage(responseMessage);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        System.out.println("Connection established");
        session.sendMessage(new TextMessage("Welcome! Connection is successful."));
    }
}