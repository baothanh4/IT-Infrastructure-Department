package com.example.IT.Infrastructure.Department.Service;

import com.example.IT.Infrastructure.Department.DTO.UserRegisterDTO;
import com.example.IT.Infrastructure.Department.Model.Users;

public interface AuthService {
    Users Register(UserRegisterDTO dto);
}
