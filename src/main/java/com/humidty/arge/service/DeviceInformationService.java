package com.humidty.arge.service;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.DeviceInformation;
import com.humidty.arge.repository.DeviceInformationRepository;
import com.humidty.arge.repository.DeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@AllArgsConstructor
public class DeviceInformationService {


    private DeviceInformationRepository deviceInformationRepository;

//    public List<DeviceInformation> getDeviceAllInfoByDeviceID(String deviceID) {
//        return deviceInformationRepository.findByDeviceID(deviceID);
//    }

    public Page<DeviceInformation> getDeviceInfoByID(String id, Integer pageNumber, Integer pageSize, Sort sort){
        int defaultPageNumber = 0;
        int defaultPageSize = 10;

        if (pageNumber == null){
            pageNumber = defaultPageNumber;
        }
        if (pageSize == null){
            pageSize = defaultPageSize;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return deviceInformationRepository.findByDeviceID(id, pageable);
    }

    public DeviceInformation createDeviceInfo(DeviceInformation newDevice){
        return deviceInformationRepository.save(newDevice);
    }


}
