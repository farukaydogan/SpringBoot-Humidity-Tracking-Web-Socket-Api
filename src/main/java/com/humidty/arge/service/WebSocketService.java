package com.humidty.arge.service;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.DeviceInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

@Service
public class WebSocketService{
    private final DeviceService deviceService;

    private final DeviceInformationService deviceInformationService;

    @Autowired
    public WebSocketService(@Lazy DeviceService deviceService, DeviceInformationService deviceInformationService) {
        this.deviceService = deviceService;
        this.deviceInformationService = deviceInformationService;
    }

    public TextMessage handleHumidity(String deviceID,double humidity) {
        //        save db humidity

        DeviceInformation deviceInformation =new DeviceInformation();
        deviceInformation.setHumidity(humidity);
        deviceInformation.setDeviceID(deviceID);

        String textMessage;

        Device device=new Device();
        // Bu değerleri kullanarak istediğiniz işlemleri yapabilirsiniz
        if (humidity < 50) {
            device.setStatus(true);
            textMessage="LED is ON";
        } else {
            device.setStatus(false);
            textMessage="LED is OFF";
        }
        deviceService.updateDevice(deviceID,device);
        deviceInformationService.createDeviceInfo(deviceInformation);
        return new TextMessage(textMessage);
    }
}