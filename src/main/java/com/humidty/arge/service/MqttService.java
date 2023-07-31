package com.humidty.arge.service;

import com.humidty.arge.helper.WateringPeriod;
import com.humidty.arge.model.Device;
import com.humidty.arge.model.Nutrient;
import com.humidty.arge.model.SensorData;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MqttService {

    private final MqttClient mqttClient;
    private final DeviceService deviceService;
    private final SensorDataService sensorDataService;
    private final NutrientService nutrientService;

    public MqttService(MqttClient mqttClient, @Lazy DeviceService deviceService, SensorDataService sensorDataService, NutrientService nutrientService) throws MqttException {
        this.mqttClient = mqttClient;
        this.deviceService = deviceService;
        this.sensorDataService = sensorDataService;
        this.nutrientService = nutrientService;
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                cause.printStackTrace();
                // Handle connection loss...
                System.out.println("baglanti koptu");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws MqttException {

                if (topic.equals(topic + "/status")) {
                    System.out.println("Status update from " + topic + ": " + new String(message.getPayload()));
                }
                // This method is called when a message arrives
//                System.out.println("Message received: " + new String(message.getPayload()));
                // get payload from message
                byte[] bytes = message.getPayload();


                String payload = new String(bytes, StandardCharsets.UTF_8);


                JSONObject jsonObj = new JSONObject(payload);

//                System.out.println(payload);
                // JSON objesinden deviceID ve humidity değerlerini alın
                String deviceId = jsonObj.getString("deviceID");


                JSONArray sensorDataList = jsonObj.getJSONArray("sensorDataList");


                String responseMessage = handeMqttData(deviceId, sensorDataList);

                System.out.println(responseMessage);
                byte[] responseBytes = responseMessage.getBytes(StandardCharsets.UTF_8);

//                 Yeni bir MqttMessage oluşturun ve yayın yapın
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

    public void onOffInfoUpdateDevice(boolean onOrOf, String deviceId) {
        Device old = deviceService.getDeviceById(deviceId);
        old.setIsOnline(onOrOf);
        deviceService.updateDevice(deviceId, old);
    }

    public String handeMqttData(String deviceId, JSONArray sensorDataList) {
        Device device = deviceService.getDeviceById(deviceId);

        if (device == null) {
            throw new RuntimeException("Device Not Found");
        }

        WateringPeriod wateringPeriod = device.getWateringPeriod();
        int startWateringHumidityThreshold = device.getStartWateringHumidityThreshold();
        int stopWateringHumidityThreshold = device.getStopWateringHumidityThreshold();
        List<Double> humidityList = new ArrayList<>();




        for (int i = 0; i < sensorDataList.length(); i++) {
            JSONObject sensorDataJson = sensorDataList.getJSONObject(i);

            SensorData sensorData = new SensorData();
            sensorData.setDeviceId(deviceId);
            sensorData.setSensorId(sensorDataJson.getString("sensorId"));
            // Check if sensor is already linked to device

            if (!device.getSensorIds().contains(sensorData.getSensorId())) {
                // Add sensor to device
                device.getSensorIds().add(sensorData.getSensorId());
            }

            double humidity = sensorDataJson.getDouble("humidity");
            sensorData.setHumidity(humidity);
            humidityList.add(humidity);

            if (!sensorDataJson.isNull("nutrient")) {
                JSONObject nutrientJson = sensorDataJson.getJSONObject("nutrient");
                Nutrient nutrient = new Nutrient();
                nutrient.setSensorId(sensorData.getSensorId());
                nutrient.setEc(nutrientJson.getDouble("ec"));
                nutrient.setPH(nutrientJson.getDouble("pH"));
                nutrient.setNitrogen(nutrientJson.getInt("nitrogen"));
                nutrient.setPotassium(nutrientJson.getDouble("potassium"));
                nutrient.setPhosphorus(nutrientJson.getDouble("phosphorus"));

                nutrient = nutrientService.createNutrient(nutrient);
                sensorData.setNutrientId(nutrient.getId());
            }
//            System.out.println(sensorData);

            sensorDataService.createSensorData(sensorData);
        }

        // nem değerleri listesi kontrolü
        boolean allAboveThreshold = humidityList.stream()
                .allMatch(humidity -> humidity > stopWateringHumidityThreshold);

        boolean allBelowThreshold = humidityList.stream()
                .allMatch(humidity -> humidity < startWateringHumidityThreshold);

        if (wateringPeriod != WateringPeriod.STOPPED) {
            if (wateringPeriod == WateringPeriod.AWAIT_WATERING && allAboveThreshold) {
                device.setWateringPeriod(WateringPeriod.WATERING);
                System.out.println("alo WATERING");
            } else if (wateringPeriod == WateringPeriod.WATERING && allBelowThreshold) {
                device.setWateringPeriod(WateringPeriod.AWAIT_SATURATION);
                System.out.println("alo AWAIT_SATURATION");
            } else if (wateringPeriod == WateringPeriod.AWAIT_SATURATION && allAboveThreshold) {
                device.setWateringPeriod(WateringPeriod.AWAIT_WATERING);
                System.out.println("alo AWAIT_WATERING");
            }
        }

        device.setLastUpdateTime(new Date());
        deviceService.updateDevice(deviceId, device);

        return deviceService.prepareStatusDeviceJson(deviceId, "handle success");
    }

}
