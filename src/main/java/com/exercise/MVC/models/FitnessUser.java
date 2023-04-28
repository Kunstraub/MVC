package com.exercise.MVC.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FitnessUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
}
