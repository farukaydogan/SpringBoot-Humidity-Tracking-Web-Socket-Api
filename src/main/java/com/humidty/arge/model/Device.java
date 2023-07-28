package com.humidty.arge.model;

//import javax.persistence.PreUpdate;
//import javax.persistence.PrePersist;
import com.humidty.arge.helper.Schedule;
import com.humidty.arge.helper.WateringPeriod;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
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

    private Boolean offWatering;
    private Boolean isOnline;
    private WateringPeriod wateringPeriod;
    private int startWateringHumidityThreshold;
    private int stopWateringHumidityThreshold;
    private List<SensorData> sensorDataList;

    private Date lastUpdateTime;
    private Date lastWateringTime;
    @CreatedDate
    private Date createDate;
    private Schedule schedule;



    public Device() {
        schedule = new Schedule();
        schedule.initializeAsTrue();
    }
}
