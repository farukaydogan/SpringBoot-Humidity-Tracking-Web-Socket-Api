package com.humidty.arge.controller;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.SensorData;
import com.humidty.arge.service.DeviceService;
import com.humidty.arge.service.SensorDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;
    private final SensorDataService sensorDataService;

    public DeviceController(DeviceService deviceService, SensorDataService sensorDataService) {
        this.deviceService = deviceService;
        this.sensorDataService = sensorDataService;
    }

    @GetMapping
    public ResponseEntity<List<Device>> getDevice() {

        return new ResponseEntity<>(deviceService.getDevice(), OK);
    }

    @GetMapping("/{id}")

    public ResponseEntity<Device> getDevice(@PathVariable String id) {
        return new ResponseEntity<>(deviceService.getDeviceById(id), OK);
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device newDevice) {
        return new ResponseEntity<>(deviceService.createDevice(newDevice), CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable String id, @RequestBody Device updateDevice) {
        deviceService.updateDevice(id, updateDevice);
        return new ResponseEntity<>(deviceService.getDeviceById(id), OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable String id) {
        deviceService.deleteDevice(id);
        return new ResponseEntity<>("Device deleted", OK);
    }

    @GetMapping("stop/{id}")
    public ResponseEntity<String> stopDevice(@PathVariable String id) throws IOException {
        deviceService.stopDevice(id);
        //gerekli socket mesahi gonderilir
        return new ResponseEntity<>("Device stopped", OK);
    }

    @GetMapping("start/{id}")
    public ResponseEntity<String> startDevice(@PathVariable String id) throws IOException {
        deviceService.startDevice(id);
        //gerekli socket mesahi gonderilir
        return new ResponseEntity<>("Device started", OK);
    }

//    @GetMapping("/{id}/sensor-data")
//    public List<SensorData> getSensorDataByDeviceId(@PathVariable String id) {
//        Device device = deviceService.getDeviceById(id);
//        List<String> sensorIds = device.getSensorIds();
//        List<SensorData> sensorDataList = new ArrayList<>();
//        for (String sensorId : sensorIds) {
//            SensorData sensorData = sensorDataService.getSensorDataById(sensorId);
//            System.out.println(sensorId);
//            sensorDataList.add(sensorData);
//        }
//        return sensorDataList;
//    }

}

