package com.humidty.arge.model;


import com.humidty.arge.helper.Schedule;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Map;

@Data
public class Device {

    @Id
    private String deviceID;
    private Boolean status;
    private Boolean stop;
    private int humidity;
    private Date lastWateringTime;
    private Schedule schedule;
    private Date createDate = new Date();

    public Device() {
        schedule = new Schedule();
    }
}
