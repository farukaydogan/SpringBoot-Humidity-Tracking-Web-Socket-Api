package com.humidty.arge.service;

import com.humidty.arge.helper.WateringPeriod;
import com.humidty.arge.model.Device;
import com.humidty.arge.model.Nutrient;
import com.humidty.arge.repository.DeviceRepository;
import com.humidty.arge.repository.NutrientRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class NutrientService {

    private final NutrientRepository nutrientRepository;

    public NutrientService(NutrientRepository nutrientRepository) {
        this.nutrientRepository = nutrientRepository;
    }

    public List<Nutrient> getAllNutrient(){
        return nutrientRepository.findAll();
    }

    public Nutrient createNutrient(Nutrient nutrient) {
        return nutrientRepository.save(nutrient);
    }

    public void deleteAll(){
        nutrientRepository.deleteAll();
    }
}
