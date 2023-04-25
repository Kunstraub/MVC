package com.exercise.MVC.controllers;

import com.exercise.MVC.models.Exercise;
import com.exercise.MVC.repositories.ExerciseRepository;
import com.exercise.MVC.repositories.IngredientRepository;
import com.exercise.MVC.repositories.NutritionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fitness")
public class TestController {

    private NutritionRepository nutritionRepository;
    private ExerciseRepository exerciseRepository;
    private IngredientRepository ingredientRepository;

    public TestController(NutritionRepository nutritionRepository, ExerciseRepository exerciseRepository, IngredientRepository ingredientRepository) {
        this.nutritionRepository = nutritionRepository;
        this.exerciseRepository = exerciseRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @PostMapping("")
    public void saveExer(@RequestBody List<Exercise> exercises){
        exerciseRepository.saveAll(exercises);
    }

   @GetMapping("/{name}")
    public Exercise readExer(@PathVariable String name){
        return exerciseRepository.findByName(name).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nix Hier!"));
    }
    @GetMapping("")
    public List<Exercise> readAllExer(){
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseRepository.findAll().forEach(exercise -> {
            exerciseList.add(exercise);
        });
        return exerciseList;
    }

    @PutMapping("/{exerciseId}")
    public void updateExer(@PathVariable Long exerciseId, @RequestBody Exercise updateExercise){
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nicht da")
        );
        exercise.setName(updateExercise.getName());
        exercise.setUrl(updateExercise.getUrl());
        exercise.setDescription(updateExercise.getDescription());
        exerciseRepository.save(exercise);
    }

    @DeleteMapping("/{exerciseId}")
    public void deleteExer(@PathVariable Long exerciseId){
        exerciseRepository.deleteById(exerciseId);
    }
}

