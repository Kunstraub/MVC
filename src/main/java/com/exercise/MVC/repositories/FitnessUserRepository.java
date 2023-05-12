package com.exercise.MVC.repositories;

import com.exercise.MVC.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FitnessUserRepository extends CrudRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
