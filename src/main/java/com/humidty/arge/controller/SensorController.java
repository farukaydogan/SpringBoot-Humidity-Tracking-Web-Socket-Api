package com.humidty.arge.controller;

import com.humidty.arge.model.Sensor;
import com.humidty.arge.repository.SensorRepository;
import com.humidty.arge.service.SensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/humidity")
public class SensorController {

    private  final SensorService sensorService;

    public SensorController(SensorService sensorService){
        this.sensorService=sensorService;
    }
    @GetMapping
    public ResponseEntity<List<Sensor>> getSensors(){

        return new ResponseEntity<>(sensorService.getSensor(),OK);
    }

    @GetMapping("/{id}")

    public ResponseEntity<Sensor>getSensor(@PathVariable String id){
        return new ResponseEntity<>(sensorService.getSensorById(id),OK);
    }

    @PostMapping
    public ResponseEntity<Sensor> createSensor(@RequestBody Sensor newSensor){
        return  new ResponseEntity<>(sensorService.createSensor(newSensor),CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable String id,@RequestBody Sensor updateSensor){
     sensorService.updateSensor(id,updateSensor);
     return new ResponseEntity<>(sensorService.getSensorById(id),OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSensor(@PathVariable String id){
        sensorService.deleteSensor(id);
        return new ResponseEntity<>("Sensor deleted",OK);
    }


//    @GetMapping("/refresh")
//    public ResponseEntity<String> refreshInformation(@RequestBody Sensor body) {
//
//        //        sensorService.updateSensor();
//        //        if sensor humidty >65 status set false
//        // if sensor humidty < 65      status set true
//    return
//    }
}
