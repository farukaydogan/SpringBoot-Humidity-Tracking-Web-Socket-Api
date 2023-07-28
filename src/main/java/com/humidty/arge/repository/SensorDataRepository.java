package com.humidty.arge.repository;

import com.humidty.arge.model.DeviceInformation;
import com.humidty.arge.model.SensorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SensorDataRepository extends MongoRepository<SensorData, String> {

        Page<SensorData> findByDeviceID(String id, Pageable pageable);
}
