package com.exercise.MVC.repositories;

import com.exercise.MVC.models.Nutrition;
import org.springframework.data.repository.CrudRepository;

public interface NutritionRepository extends CrudRepository<Nutrition,Long> {
}
