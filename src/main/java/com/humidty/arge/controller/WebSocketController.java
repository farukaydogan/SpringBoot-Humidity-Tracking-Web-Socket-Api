package com.humidty.arge.controller;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.DeviceInformation;
import com.humidty.arge.service.DeviceInformationService;
import com.humidty.arge.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;

@Controller
public class WebSocketController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceInformationService deviceInformationService;
    public WebSocketController(DeviceService deviceService,DeviceInformationService deviceInformationService){
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