package com.humidty.arge.helper;

public enum WateringPeriod {
    AWAIT_WATERING("Awaiting Watering"),
    WATERING("Watering"),
    STOPPED("Device Stopped"),

    AWAIT_SATURATION("Saturation Reached, Awaiting Drying");
    private final String displayName;

    WateringPeriod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}