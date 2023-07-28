package com.humidty.arge.controller;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.SensorData;
import com.humidty.arge.repository.DeviceInformationRepository;
import com.humidty.arge.service.SensorDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
@RestController
@RequestMapping("/sensor-data")
public class DeviceSensorController {

    private SensorDataService sensorDataService;

    @GetMapping("/{id}")
    public ResponseEntity<SensorData> getSensorDataById(@PathVariable String id) {
        SensorData sensorData = sensorDataService.getSensorDataById(id);
        if (sensorData != null) {
            return new ResponseEntity<>(sensorData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<SensorData> createSensorData(@RequestBody SensorData newSensorData) {
        SensorData createdSensorData = sensorDataService.createSensorData(newSensorData);
        return new ResponseEntity<>(createdSensorData, HttpStatus.CREATED);
    }


}
