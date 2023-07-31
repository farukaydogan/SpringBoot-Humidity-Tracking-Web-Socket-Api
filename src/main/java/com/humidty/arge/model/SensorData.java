package com.humidty.arge.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;



@Data
public class SensorData {
        private Long sensorId;
        @Indexed
        private Nutrient nutrientId;
        @Indexed
        private String deviceId;

        private double humidity;

        @CreatedDate // This annotation will automatically set the creation date when saving the document
        private Date date;
        // Constructor, getters, setters...
}
