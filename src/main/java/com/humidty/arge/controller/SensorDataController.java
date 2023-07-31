package com.humidty.arge.controller;

import com.humidty.arge.model.SensorData;
import com.humidty.arge.service.SensorDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sensor-data")
public class SensorDataController {

    private SensorDataService sensorDataService;

    @GetMapping("/{id}")
    public ResponseEntity<List<SensorData>>  getSensorDataById(@PathVariable String id,
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

        Page<SensorData>  sensorDataPage = sensorDataService.getSensorDataById(id, pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "date"));

        List<SensorData> sensorDataList = sensorDataPage.getContent();


        if (!sensorDataList.isEmpty()) {
            return new ResponseEntity<>(sensorDataList, HttpStatus.OK);
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
