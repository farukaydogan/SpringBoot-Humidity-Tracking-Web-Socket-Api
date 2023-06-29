package com.humidty.arge.model;


import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Sensor {
    @Id
    private  String id;
    private Boolean status;
    private Double humidity;
    private Date date = new Date();


}
