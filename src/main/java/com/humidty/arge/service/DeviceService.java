package com.humidty.arge.service;

import com.humidty.arge.model.Device;
import com.humidty.arge.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

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
        deviceRepository.save(oldDevice);
    }

    public void deleteDevice(String id) {
        Device device = getDeviceById(id);
        deviceRepository.delete(device);
    }

    public void stopDevice(String id) {
        Device oldDevice = getDeviceById(id);

        try {
            sessionManagementService.sendMessageToDevice(id, "start"); // or any other message you want to send
        } catch (IOException e) {
            // handle the exception
            System.err.println("Error while sending a message to the device: " + e.getMessage());
        }
        oldDevice.setStatus(false);
        oldDevice.setStop(true);
        deviceRepository.save(oldDevice);
    }

    public void startDevice(String id) {
        Device oldDevice = getDeviceById(id);

        try {
            sessionManagementService.sendMessageToDevice(id, "start"); // or any other message you want to send
        } catch (IOException e) {
            // handle the exception
            System.err.println("Error while sending a message to the device: " + e.getMessage());
        }

        oldDevice.setStatus(true);
        oldDevice.setStop(false);
        deviceRepository.save(oldDevice);

    }

}
