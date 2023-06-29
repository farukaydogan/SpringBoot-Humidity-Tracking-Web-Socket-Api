package com.humidty.arge.service;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.DeviceInformation;
import com.humidty.arge.repository.DeviceInformationRepository;
import com.humidty.arge.repository.DeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class DeviceInformationService {


    private DeviceInformationRepository deviceInformationRepository;

    public List<DeviceInformation> getDeviceAllInfoByDeviceID(DeviceInformation deviceInformation) {
        return  deviceInformationRepository.findByDeviceID(deviceInformation.getDeviceID());
    }

    public  List<DeviceInformation> getDeviceInfoByID(String id){
        return deviceInformationRepository.findByDeviceID(id);
    }

    public DeviceInformation createDeviceInfo(DeviceInformation newDevice){
        return deviceInformationRepository.save(newDevice);
    }

}
