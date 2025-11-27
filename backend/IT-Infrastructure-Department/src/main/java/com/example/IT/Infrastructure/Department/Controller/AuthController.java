package com.example.IT.Infrastructure.Department.Controller;

import com.example.IT.Infrastructure.Department.DTO.JwtResponse;
import com.example.IT.Infrastructure.Department.DTO.LoginRequestDTO;
import com.example.IT.Infrastructure.Department.DTO.RegisterRequestDTO;
import com.example.IT.Infrastructure.Department.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {
        authService.register(dto);
        return ResponseEntity.ok().body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequestDTO dto) {
        JwtResponse jwtResponse = authService.login(dto);
        return ResponseEntity.ok().body(jwtResponse);
    }
//
//    @PostMapping("/refresh-token")
//    public ResponseEntity<?> refreshToken(@RequestBody Map<String,String> body){
//        String refreshToken = body.get("refresh-token");
//
//    }
}
