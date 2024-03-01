package com.exercise_1.controller;

import com.exercise_1.model.User;
import com.exercise_1.service.UserService;
import com.exercise_1.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User authenticatedUser = userService.findByEmail(authentication.getName()).orElseThrow(() ->
                new RuntimeException("Error: Usuario no encontrado."));

        String token = jwtUtil.generateToken(authenticatedUser.getEmail(), authenticatedUser.getName(), authenticatedUser.getRole().toString());

        return ResponseEntity.ok().header("Authorization", "Bearer " + token)
                .body(Map.of("token", token, "name", authenticatedUser.getName(), "email", authenticatedUser.getEmail(), "role", authenticatedUser.getRole()));
    }
}
