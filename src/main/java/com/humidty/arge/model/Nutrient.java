package com.humidty.arge.model;

import org.springframework.data.annotation.Id;

public class Nutrient {

    @Id
    private long id;
    private double ec;
    private double pH;
    private int nitrogen;
    private double potassium;
    private double phosphorus;
}
