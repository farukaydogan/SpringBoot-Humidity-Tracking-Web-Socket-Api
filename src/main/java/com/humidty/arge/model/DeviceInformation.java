package com.humidty.arge.model;


import com.humidty.arge.helper.FormatDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

import java.util.Date;

@Data
public class DeviceInformation {

    @Id
    private String id;
    @Indexed
    private String deviceID;
    private Double humidity;
    private Date date;

    // Diğer getter ve setter metodları

    public String getFormattedDate() {
        return FormatDateTime.format(date);
    }
}
