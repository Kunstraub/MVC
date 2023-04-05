package com.exercise.MVC.dto;

import com.exercise.MVC.models.Exercise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseDto {
    private Long id;
    private String name;
    private String url;
    private String description;

    public static Exercise toEntity(ExerciseDto exerciseDto){
        final Exercise exercise = new Exercise();
        exercise.setId(exerciseDto.getId());
        exercise.setName(exerciseDto.getName());
        exercise.setUrl(exerciseDto.getUrl());
        exercise.setDescription(exerciseDto.getDescription());
        return exercise;
    }

    public static ExerciseDto fromEntity(Exercise exercise){
        return ExerciseDto.builder()
                .id(exercise.getId())
                .url(exercise.getUrl())
                .description(exercise.getDescription())
                .name(exercise.getName()).build();
    }
}
