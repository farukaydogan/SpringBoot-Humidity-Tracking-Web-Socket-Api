package com.humidty.arge.repository;

import com.humidty.arge.model.DeviceInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceInformationRepository extends MongoRepository<DeviceInformation, String> {
    Page<DeviceInformation> findByDeviceID(String id, Pageable pageable);
}
