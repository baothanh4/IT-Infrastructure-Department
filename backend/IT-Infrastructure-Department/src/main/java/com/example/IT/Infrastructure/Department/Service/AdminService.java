package com.example.IT.Infrastructure.Department.Service;

import com.example.IT.Infrastructure.Department.DTO.UserResponseDTO;

import java.util.List;

public interface AdminService {
    List<UserResponseDTO> getAllUsers();
}
