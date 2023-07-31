package com.humidty.arge.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class Nutrient {

    @Id
    private String id;

    @Indexed
    private String sensorId;
    private double ec;
    private double pH;
    private int nitrogen;
    private double potassium;
    private double phosphorus;
}
