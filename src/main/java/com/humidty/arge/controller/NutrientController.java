package com.humidty.arge.controller;

import com.humidty.arge.model.Nutrient;
import com.humidty.arge.model.SensorData;
import com.humidty.arge.repository.DeviceRepository;
import com.humidty.arge.repository.NutrientRepository;
import com.humidty.arge.service.NutrientService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/nutrient")
public class NutrientController {

    private final NutrientService nutrientService;

    public NutrientController(NutrientService nutrientService) {
        this.nutrientService = nutrientService;
    }

    @GetMapping
    public ResponseEntity<List<Nutrient>> getAllNutrient(){

        return new ResponseEntity<>(nutrientService.getAllNutrient(), OK);
    }

    @DeleteMapping
    public ResponseEntity<String>deleteAll(){
        nutrientService.deleteAll();
        return new ResponseEntity<>("Deleted", OK);
    }
}
