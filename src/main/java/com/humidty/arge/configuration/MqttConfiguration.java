package com.humidty.arge.configuration;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfiguration {
    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.clientId}")
    private String clientId;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.topic}")
    private String topic;

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());
        connOpts.setCleanSession(true);
        connOpts.setWill(topic + "/status", "Offline".getBytes(), 2, true);  // <-- Add this line for LWT
        return connOpts;
    }

//    @Bean
//    public MqttClient mqttClient() throws MqttException {
//        MqttClient mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
//        mqttClient.connect(mqttConnectOptions());
//        return mqttClient;
//    }
//    @Bean
    @Bean
    public MqttClient mqttClient() {
        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setWill("/status", "Offline".getBytes(), 2, true);
            connOpts.setCleanSession(true);
            mqttClient.connect(connOpts);
            return mqttClient;
        } catch (MqttException e) {
            throw new RuntimeException("Could not connect to MQTT broker", e);
        }
    }


}
