package com.humidty.arge.service;

import com.humidty.arge.model.Device;
import com.humidty.arge.repository.DeviceRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service

public class DeviceService {

    private final DeviceRepository deviceRepository;


    @Autowired
    private SessionManagementService sessionManagementService;


    // constructor injection
    public DeviceService(DeviceRepository deviceRepository, ApplicationEventPublisher eventPublisher) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getDevice() {
        return deviceRepository.findAll();
    }


    public Device getDeviceById(String id) {
        return deviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Device Not Found"));
    }

    public Device createDevice(Device device) {
        Device newDevice = new Device();
        newDevice.setDeviceID(device.getDeviceID());
        newDevice.setCreateDate(new Date());
        newDevice.setWateringSituation(false);
        newDevice.setOffWatering(false);
        return deviceRepository.save(newDevice);
    }

    public void updateDevice(String id, Device updateDevice) {
        Device oldDevice = getDeviceById(id);
        oldDevice.setWateringSituation(updateDevice.getWateringSituation());


        if (updateDevice.getHumidity()!=0){
            oldDevice.setHumidity(updateDevice.getHumidity());
        }

        if (updateDevice.getIsOnline()!=null){
            oldDevice.setIsOnline(updateDevice.getIsOnline());
        }

        if (updateDevice.getLastUpdateTime()!=null){
            oldDevice.setLastUpdateTime(updateDevice.getLastUpdateTime());
        }
        if (!updateDevice.getSchedule().getDailySchedule().isEmpty()){
            oldDevice.setSchedule(updateDevice.getSchedule());
        }

        if (updateDevice.getLastWateringTime()!=null){
            oldDevice.setLastWateringTime(updateDevice.getLastWateringTime());
        }
        deviceRepository.save(oldDevice);
    }

    public void deleteDevice(String id) {
        Device device = getDeviceById(id);
        deviceRepository.delete(device);
    }

    public void startDevice(String id) throws IOException {

        Device oldDevice = getDeviceById(id);
        oldDevice.setWateringSituation(true);
        oldDevice.setOffWatering(false);
        deviceRepository.save(oldDevice);

        // Send start message to device
        WebSocketSession deviceSession = sessionManagementService.getSessionById(id);

        if (deviceSession != null && deviceSession.isOpen()) {
            deviceSession.sendMessage(prepareStatusDeviceJson(id,"Device Is Started"));
        } else {
            System.out.println("startElse");
        }
    }

    public void stopDevice(String id) throws IOException {
        Device oldDevice = getDeviceById(id);
        oldDevice.setWateringSituation(false);
        oldDevice.setOffWatering(true);
        deviceRepository.save(oldDevice);

        // Send stop message to device
        WebSocketSession deviceSession = sessionManagementService.getSessionById(id);
        if (deviceSession != null && deviceSession.isOpen()) {
            deviceSession.sendMessage(prepareStatusDeviceJson(id,"Device Is Stopped"));
        } else {
            System.out.println("stopElse");
        }
    }

    public TextMessage prepareStatusDeviceJson(String id,String message){
        JSONObject status = new JSONObject();

        Device device =getDeviceById(id);

        status.put("status", device.getWateringSituation());

        status.put("stop", device.getOffWatering());

        status.put("message",message);

        return new TextMessage(status.toString());
    }


}
