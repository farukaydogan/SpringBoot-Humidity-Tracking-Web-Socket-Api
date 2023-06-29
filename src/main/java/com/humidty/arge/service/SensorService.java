package com.humidty.arge.service;

import com.humidty.arge.model.Sensor;
import com.humidty.arge.repository.SensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SensorService {

    private SensorRepository sensorRepository;

    public List<Sensor> getSensor() {
        return sensorRepository.findAll();
    }

    public Sensor createSensor(Sensor newSensor) {
        return sensorRepository.save(newSensor);
    }

    public Sensor getSensorById(String id) {
        return sensorRepository.findById(id).orElseThrow(() -> new RuntimeException("Sensor Not Found"));
    }

    public void updateSensor(String id,Sensor updateSensor) {
        Sensor oldSensor=getSensorById(id);
        oldSensor.setHumidity(updateSensor.getHumidity());
        oldSensor.setStatus(updateSensor.getStatus());
        sensorRepository.save(oldSensor);
    }

    public void deleteSensor(String id) {
        Sensor sensor=getSensorById(id);
        sensorRepository.delete(sensor);
    }
}
