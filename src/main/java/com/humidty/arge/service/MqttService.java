package com.humidty.arge.service;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.eclipse.paho.client.mqttv3.MqttMessage;
@Service
public class MqttService {

    private final MqttClient mqttClient;

    public MqttService(MqttClient mqttClient) throws MqttException {
        this.mqttClient = mqttClient;
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                // Handle connection loss...
                System.out.println("baglanti koptu");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                // This method is called when a message arrives
                System.out.println("Message received: " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("mesaj gonderildi");
            }
        });

        try {
            // Subscribe to the topic 'test_topic'
            mqttClient.subscribe("test_topic");
            System.out.println("alo start");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
