package com.humidty.arge.model;


import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Device {

    @Id
    private  String deviceID;
    private Boolean status;
    private Boolean stop;
    private Double humidity;

    private Date createDate = new Date();


}
