package com.humidty.arge.service;

import com.humidty.arge.helper.WateringPeriod;
import com.humidty.arge.model.Device;
import com.humidty.arge.model.DeviceInformation;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.socket.TextMessage;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class MqttService {

    private final MqttClient mqttClient;

    public MqttService(MqttClient mqttClient, @Lazy DeviceService deviceService, DeviceInformationService deviceInformationService) throws MqttException {
        this.mqttClient = mqttClient;
        this.deviceService = deviceService;
        this.deviceInformationService = deviceInformationService;
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                // Handle connection loss...
                System.out.println("baglanti koptu");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws MqttException {
                // This method is called when a message arrives
                System.out.println("Message received: " + new String(message.getPayload()));
                // get payload from message
                byte[] bytes = message.getPayload();


                String payload = new String(bytes, StandardCharsets.UTF_8);


                JSONObject jsonObj = new JSONObject(payload);

                // JSON objesinden deviceID ve humidity değerlerini alın
                String deviceId = jsonObj.getString("deviceID");

                // Eğer deviceID, kabul edilenler listesinde değilse, bağlantıyı kapat
//                if (!acceptedDeviceIds.contains(deviceId)) {
//                    return;
//                }

                double humidity = jsonObj.getDouble("humidity");


                String responseMessage = handleHumidity(deviceId,humidity);

                System.out.println(responseMessage);
                byte[] responseBytes = responseMessage.getBytes(StandardCharsets.UTF_8);

        // Yeni bir MqttMessage oluşturun ve yayın yapın
                MqttMessage mqttMessage = new MqttMessage(responseBytes);
                mqttClient.publish("response_topic", mqttMessage);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
//                System.out.println("mesaj gonderildi");
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
    private final DeviceService deviceService;

    private final DeviceInformationService deviceInformationService;

    public void onOffInfoUpdateDevice(boolean onOrOf,String deviceId){
        Device old= deviceService.getDeviceById(deviceId);
        old.setIsOnline(onOrOf);
        deviceService.updateDevice(deviceId,old);
    }
    public String handleHumidity(String deviceID, double humidity) {
        //        save db humidity

        DeviceInformation deviceInformation =new DeviceInformation();
        deviceInformation.setHumidity(humidity);
        deviceInformation.setDeviceID(deviceID);


        Device device= deviceService.getDeviceById(deviceID);

        WateringPeriod wateringPeriod =device.getWateringPeriod();
        int startWateringHumidityThreshold = device.getStartWateringHumidityThreshold();
        int stopWateringHumidityThreshold = device.getStopWateringHumidityThreshold();

        System.out.println(wateringPeriod);
        System.out.println(startWateringHumidityThreshold);
        System.out.println(stopWateringHumidityThreshold);

        if (wateringPeriod != WateringPeriod.STOPPED){
            if (wateringPeriod == WateringPeriod.AWAIT_WATERING) {
                device.setWateringPeriod(WateringPeriod.WATERING);
                System.out.println("alo WATERING");
            } else if (wateringPeriod == WateringPeriod.WATERING && humidity >= stopWateringHumidityThreshold) {
                device.setWateringPeriod(WateringPeriod.AWAIT_SATURATION);
                System.out.println("alo AWAIT_SATURATION");
            } else if (wateringPeriod == WateringPeriod.AWAIT_SATURATION && humidity <=
                    startWateringHumidityThreshold) {
                device.setWateringPeriod(WateringPeriod.AWAIT_WATERING);
                System.out.println("alo AWAIT_WATERING");
            }

        }

        // Bu değerleri kullanarak istediğiniz işlemleri yapabilirsiniz

        device.setLastUpdateTime(new Date());
        deviceService.updateDevice(deviceID,device);

        deviceInformationService.createDeviceInfo(deviceInformation);

        return deviceService.prepareStatusDeviceJson(deviceID,"handle success");
    }}
