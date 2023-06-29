package com.humidty.arge.controller;


import com.humidty.arge.model.Device;
import com.humidty.arge.model.DeviceInformation;
import com.humidty.arge.service.DeviceInformationService;
import com.humidty.arge.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/device-info")
public class DeviceInformationController {
    private final DeviceInformationService deviceInformationService;

    public DeviceInformationController(DeviceInformationService deviceInformationService) {
        this.deviceInformationService = deviceInformationService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<DeviceInformation>> getDeviceInfoById(@PathVariable String id) {
        return new ResponseEntity<>(deviceInformationService.getDeviceInfoByID(id), OK);
    }

    @PostMapping
    public ResponseEntity<DeviceInformation> addHumidityOnDevice(@RequestBody DeviceInformation newDevice) {
        return new ResponseEntity<>(deviceInformationService.createDeviceInfo(newDevice), CREATED);
    }

//    @PutMapping("{id}")
//    public ResponseEntity<Device> updateDevice(@PathVariable String id, @RequestBody Device updateDevice) {
//        deviceService.updateDevice(id, updateDevice);
//        return new ResponseEntity<>(deviceService.getDeviceById(id), OK);
//    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<String> deleteDevice(@PathVariable String id) {
//        deviceService.deleteDevice(id);
//        return new ResponseEntity<>("Device deleted", OK);
//    }
}