package com.humidty.arge.repository;

import com.humidty.arge.model.Sensor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SensorRepository extends MongoRepository<Sensor,String> {

}
