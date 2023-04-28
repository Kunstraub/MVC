package com.exercise.MVC.repositories;

import com.exercise.MVC.models.FitnessUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FitnessUserRepository extends CrudRepository<FitnessUser,Long> {
    Optional<FitnessUser> findByEmail(String email);
}
