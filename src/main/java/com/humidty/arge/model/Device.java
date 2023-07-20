package com.humidty.arge.model;

//import javax.persistence.PreUpdate;
//import javax.persistence.PrePersist;
import com.humidty.arge.helper.Schedule;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Map;

@Data
public class Device {

//    @PreUpdate
//    @PrePersist
//    public void updateTimestamps() {
//        lastUpdateTime = new Date();
//    }
    @Id
    private String deviceID;
    private Boolean wateringSituation;
    private Boolean offWatering;
    private Boolean isOnline;
    private int humidity;
    private Date lastUpdateTime;
    private Date lastWateringTime;
    private Date createDate;
    private Schedule schedule;



    public Device() {
        schedule = new Schedule();
        schedule.initializeAsTrue();
    }
}
