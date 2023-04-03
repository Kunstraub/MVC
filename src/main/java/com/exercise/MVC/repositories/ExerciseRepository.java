package com.exercise.MVC.repositories;

import com.exercise.MVC.models.Exercise;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExerciseRepository extends CrudRepository<Exercise,Long> {
    Optional<Exercise> findByName(String name);
}
