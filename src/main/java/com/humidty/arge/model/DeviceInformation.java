package com.humidty.arge.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Data
public class DeviceInformation {

    @Id
    private String id;
    @Indexed
    private String deviceID;
    private Double humidity;
    private Date date = new Date();

}
