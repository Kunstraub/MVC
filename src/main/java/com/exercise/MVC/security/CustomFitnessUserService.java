package com.exercise.MVC.security;


import com.exercise.MVC.exception.EntityNotFoundException;
import com.exercise.MVC.models.FitnessUser;
import com.exercise.MVC.repositories.FitnessUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomFitnessUserService implements UserDetailsService {

    private FitnessUserRepository fitnessUserRepository;

    public CustomFitnessUserService(FitnessUserRepository fitnessUserRepository) {
        this.fitnessUserRepository = fitnessUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FitnessUser fitnessUser = fitnessUserRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("FitnessUser ist nicht vorhanden!!! Bitte andere E-Mail probieren!"));

        return  new User(fitnessUser.getEmail(), fitnessUser.getPassword(), Collections.emptyList());
    }
}
