package com.humidty.arge.controller;

import com.humidty.arge.model.Device;
import com.humidty.arge.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<List<Device>> getDevice() {

        return new ResponseEntity<>(deviceService.getDevice(), OK);
    }

    @GetMapping("/{id}")

    public ResponseEntity<Device> getDevice(@PathVariable String id) {
        return new ResponseEntity<>(deviceService.getDeviceById(id), OK);
    }

//    @PostMapping
//    public ResponseEntity<Device> addHumidityOnDevice(@RequestBody Device newDevice) {
//        return new ResponseEntity<>(deviceService.addHumidityOnDevice(newDevice), CREATED);
//    }

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
}

