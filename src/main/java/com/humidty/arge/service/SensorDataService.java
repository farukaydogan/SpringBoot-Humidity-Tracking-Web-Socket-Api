package com.humidty.arge.service;

import com.humidty.arge.model.SensorData;
import com.humidty.arge.repository.SensorDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SensorDataService {

    private SensorDataRepository sensorDataRepository;

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
        return sensorDataRepository.findByDeviceID(deviceId, pageable);
    }

    public SensorData createSensorData(SensorData newSensorData) {
        return sensorDataRepository.save(newSensorData);
    }
}
