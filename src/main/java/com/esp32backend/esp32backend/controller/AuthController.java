package com.esp32backend.esp32backend.controller;

import com.esp32backend.esp32backend.dto.LoginRequest;
import com.esp32backend.esp32backend.dto.LoginResponse;
import com.esp32backend.esp32backend.model.User;
import com.esp32backend.esp32backend.service.JwtService;
import com.esp32backend.esp32backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return userService.findByUsername(request.getUsername())
                .filter(user -> userService.checkPassword(user, request.getPassword()))
                .map(user -> ResponseEntity.ok(new LoginResponse(jwtService.generateToken(user.getUsername()))))
                .orElse(ResponseEntity.status(401).body("Usuario o contraseña incorrecta"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
        if (userService.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }
        User user = userService.register(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(user);
    }
}

