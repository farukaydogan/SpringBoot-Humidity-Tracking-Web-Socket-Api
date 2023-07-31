package com.humidty.arge.service;

import com.humidty.arge.model.SensorData;
import com.humidty.arge.repository.SensorDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SensorDataService {

    private  SensorDataRepository sensorDataRepository;


    public List<SensorData> getSensorDatasAll() {
        return sensorDataRepository.findAll();
    }

    public Page<SensorData> getSensorDataById(String deviceId, Integer pageNumber, Integer pageSize, Sort sort) {
        int defaultPageNumber = 0;
        int defaultPageSize = 10;

        if (pageNumber == null) {
            pageNumber = defaultPageNumber;
        }
        if (pageSize == null) {
            pageSize = defaultPageSize;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return sensorDataRepository.findByDeviceId(deviceId, pageable);
    }

    public SensorData getSensorDataById(String deviceId) {
        return sensorDataRepository.findByDeviceId(deviceId);
    }


    public SensorData createSensorData(SensorData newSensorData) {
        // deviceId'ye göre Device'ı bul

        // Yeni SensorData'ı kaydet ve döndür
        return sensorDataRepository.save(newSensorData);
    }
    public void deleteAll(){
        sensorDataRepository.deleteAll();
    }
}
