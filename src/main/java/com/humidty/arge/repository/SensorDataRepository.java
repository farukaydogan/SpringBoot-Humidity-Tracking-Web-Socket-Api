package com.humidty.arge.repository;

import com.humidty.arge.model.SensorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SensorDataRepository extends MongoRepository<SensorData, String> {

        Page<SensorData> findByDeviceId(String id, Pageable pageable);
        SensorData findByDeviceId(String id);
}
