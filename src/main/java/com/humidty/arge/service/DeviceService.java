package com.humidty.arge.service;

import com.humidty.arge.model.Device;
import com.humidty.arge.repository.DeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeviceService {

    private DeviceRepository deviceRepository;

    public List<Device> getDevice() {
        return deviceRepository.findAll();
    }


    public Device getDeviceById(String id) {
        return deviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Device Not Found"));
    }

    public void updateDevice(String id, Device updateDevice) {
        Device oldDevice =getDeviceById(id);
        oldDevice.setHumidity(updateDevice.getHumidity());
        oldDevice.setStatus(updateDevice.getStatus());
        deviceRepository.save(oldDevice);
    }

    public void deleteDevice(String id) {
        Device device =getDeviceById(id);
        deviceRepository.delete(device);
    }
}
