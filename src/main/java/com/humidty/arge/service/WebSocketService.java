package com.humidty.arge.service;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.DeviceInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.Date;

@Service
public class WebSocketService{
    private final DeviceService deviceService;

    private final DeviceInformationService deviceInformationService;


    @Autowired
    public WebSocketService(@Lazy DeviceService deviceService, DeviceInformationService deviceInformationService) {
        this.deviceService = deviceService;
        this.deviceInformationService = deviceInformationService;
    }

    public void onOffInfoUpdateDevice(boolean onOrOf,String deviceId){
        Device old= deviceService.getDeviceById(deviceId);
        old.setIsOnline(onOrOf);
        deviceService.updateDevice(deviceId,old);
    }
    public TextMessage handleHumidity(String deviceID,double humidity) {
        //        save db humidity

        DeviceInformation deviceInformation =new DeviceInformation();
        deviceInformation.setHumidity(humidity);
        deviceInformation.setDeviceID(deviceID);


        Device device=new Device();
        // Bu değerleri kullanarak istediğiniz işlemleri yapabilirsiniz
        if (humidity < device.getHumidity()) {
            device.setWateringSituation(true);
            device.setLastWateringTime(new Date());
        } else {
            device.setWateringSituation(false);
        }
        device.setLastUpdateTime(new Date());
        deviceService.updateDevice(deviceID,device);

        deviceInformationService.createDeviceInfo(deviceInformation);

        return deviceService.prepareStatusDeviceJson(deviceID,"handle success");
    }
}