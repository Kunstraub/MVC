package com.exercise.MVC.repositories;

import com.exercise.MVC.models.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient,Long> {
}
