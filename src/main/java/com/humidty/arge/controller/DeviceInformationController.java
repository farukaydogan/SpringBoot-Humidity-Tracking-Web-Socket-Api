package com.humidty.arge.controller;


import com.humidty.arge.model.DeviceInformation;
import com.humidty.arge.service.DeviceInformationService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/device-info")
public class DeviceInformationController {
    private final DeviceInformationService deviceInformationService;

    public DeviceInformationController(DeviceInformationService deviceInformationService) {
        this.deviceInformationService = deviceInformationService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Page<DeviceInformation>> getDeviceInfoLastProcessById(@PathVariable String id,
                                                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        int defaultPageNumber = 0;
        int defaultPageSize = 10;

        if (pageNumber == null) {
            pageNumber = defaultPageNumber;
        }
        if (pageSize == null) {
            pageSize = defaultPageSize;
        }

       Page<DeviceInformation> deviceInfoPage = deviceInformationService.getDeviceInfoByID(id, pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "date"));


        return new ResponseEntity<>(deviceInfoPage, HttpStatus.OK);
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