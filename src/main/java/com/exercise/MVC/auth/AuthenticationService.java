package com.exercise.MVC.auth;

import com.exercise.MVC.models.FitnessUser;
import com.exercise.MVC.models.Role;
import com.exercise.MVC.repositories.FitnessUserRepository;
import com.exercise.MVC.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final FitnessUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        //userRepository.save(user);
        return null;

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
