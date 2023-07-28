package com.humidty.arge.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;



public class SensorData {
        private Long id;
        private String sensorId;
        @Indexed
        private String deviceId;
        private double humidity;
        private double ec;
        private double pH;
        private int nitrogen;
        private double potassium;
        private double phosphorus;
        @CreatedDate // This annotation will automatically set the creation date when saving the document
        private Date date;
        // Constructor, getters, setters...
}
