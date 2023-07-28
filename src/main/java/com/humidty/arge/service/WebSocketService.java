package com.humidty.arge.service;

import com.humidty.arge.helper.WateringPeriod;
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
    public String  handleHumidity(String deviceID,double humidity) {
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
    }
}