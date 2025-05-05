package com.doublev.prueba.hotel.controller;

import com.doublev.prueba.hotel.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Permitir solicitudes desde cualquier origen (ajustable)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String token = jwtUtil.generarToken(userDetails.getUsername());

            return ResponseEntity.ok(Map.of("token", token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        }
    }
}