package com.exercise.MVC.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Nutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany
    private List<Ingredient> ingredientList;





}
