package com.humidty.arge.controller;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.Nutrient;
import com.humidty.arge.model.SensorData;
import com.humidty.arge.service.DeviceService;
import com.humidty.arge.service.SensorDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/sensor-data")
public class SensorDataController {

    private final SensorDataService sensorDataService;
    private final DeviceService deviceService;

    public SensorDataController(SensorDataService sensorDataService,DeviceService deviceService) {
        this.sensorDataService = sensorDataService;
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<List<SensorData>> getAllSensorDatas(){
        return new ResponseEntity<>(sensorDataService.getSensorDatasAll(), OK);
    }

//    @GetMapping
//    public ResponseEntity<List<SensorData>> getDevice() {
//        return new ResponseEntity<>(sensorDataService.getSensorDataById(), OK);
//    }

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

        String sensorId=newSensorData.getSensorId();
        String deviceId=newSensorData.getDeviceId();
        Device device = deviceService.getDeviceById(deviceId);
        SensorData createdSensorData = sensorDataService.createSensorData(newSensorData);
        // Eğer Device bulunamadıysa hata fırlat

        if (device == null) {
            throw  new RuntimeException("Device Not Found");
        }
        // Device'ın sensorIds listesini güncelle
        List<String> sensorIds = device.getSensorIds();


        // burada eger arrayin icerisinde yoksa diyip findleyecegiz
        if (sensorIds == null) {
            sensorIds = new ArrayList<>();
        }

        // Eğer sensorIds listesi bu sensorId'yi içermiyorsa sensorId'yi listeye ekle
        if (!sensorIds.contains(sensorId)) {
            sensorIds.add(sensorId);
            device.setSensorIds(sensorIds);
        }

        deviceService.updateDevice(deviceId,device);



        return new ResponseEntity<>(createdSensorData, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll(){
        sensorDataService.deleteAll();
        return new ResponseEntity<>("Deleted", OK);
    }
}
