package com.humidty.arge.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;


@Data
public class SensorData {

        @Id
        private String id;

        private String  sensorId;
        @Indexed
        private String deviceId;

        private double humidity;
        private String nutrientId;

        @CreatedDate // This annotation will automatically set the creation date when saving the document
        private Date date;
        // Constructor, getters, setters...
}
