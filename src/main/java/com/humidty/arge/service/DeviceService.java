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
        newDevice.setStatus(false);
        newDevice.setStop(false);
        return deviceRepository.save(newDevice);
    }

    public void updateDevice(String id, Device updateDevice) {
        Device oldDevice = getDeviceById(id);
        oldDevice.setStatus(updateDevice.getStatus());
        oldDevice.setHumidity(updateDevice.getHumidity());
        oldDevice.setSchedule(updateDevice.getSchedule());
        deviceRepository.save(oldDevice);
    }

    public void deleteDevice(String id) {
        Device device = getDeviceById(id);
        deviceRepository.delete(device);
    }

    public void startDevice(String id) throws IOException {

        Device oldDevice = getDeviceById(id);
        oldDevice.setStatus(true);
        oldDevice.setStop(false);
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
        oldDevice.setStatus(false);
        oldDevice.setStop(true);
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

        status.put("status", device.getStatus());

        status.put("stop", device.getStop());

        status.put("message",message);

        return new TextMessage(status.toString());
    }


}
