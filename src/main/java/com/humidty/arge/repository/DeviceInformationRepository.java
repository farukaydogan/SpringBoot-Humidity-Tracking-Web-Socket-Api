package com.humidty.arge.repository;

import com.humidty.arge.model.DeviceInformation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceInformationRepository extends MongoRepository<DeviceInformation, String> {
    List<DeviceInformation> findByDeviceID(String deviceID);
}
