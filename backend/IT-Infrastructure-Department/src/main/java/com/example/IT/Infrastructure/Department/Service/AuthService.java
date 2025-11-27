package com.example.IT.Infrastructure.Department.Service;

import com.example.IT.Infrastructure.Department.DTO.JwtResponse;
import com.example.IT.Infrastructure.Department.DTO.LoginRequestDTO;
import com.example.IT.Infrastructure.Department.DTO.RegisterRequestDTO;

public interface AuthService {
    void register(RegisterRequestDTO dto);

    JwtResponse login(LoginRequestDTO dto);
}
