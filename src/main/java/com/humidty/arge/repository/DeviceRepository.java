package com.humidty.arge.repository;

import com.humidty.arge.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceRepository extends MongoRepository<Device,String> {

}
