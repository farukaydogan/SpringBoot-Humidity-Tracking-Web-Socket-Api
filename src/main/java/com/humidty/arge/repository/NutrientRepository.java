package com.humidty.arge.repository;

import com.humidty.arge.model.Nutrient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NutrientRepository extends MongoRepository<Nutrient, String> {
//        Optional<Nutrient> findById(String id);
}
