package com.example.IT.Infrastructure.Department.Service;

import com.example.IT.Infrastructure.Department.DTO.JwtResponse;
import com.example.IT.Infrastructure.Department.DTO.LoginRequestDTO;
import com.example.IT.Infrastructure.Department.DTO.RegisterRequestDTO;
import com.example.IT.Infrastructure.Department.DTO.RegisterResponseDTO;

public interface AuthService {
    RegisterResponseDTO register(RegisterRequestDTO dto);

    JwtResponse login(LoginRequestDTO dto);
}
