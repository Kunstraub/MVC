package com.exercise.MVC.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int amount;
    @ManyToOne
    @JoinColumn(name = "nutritionId")
    private Nutrition nutrition;



}
