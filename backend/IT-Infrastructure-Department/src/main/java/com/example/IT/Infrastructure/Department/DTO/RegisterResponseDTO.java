package com.example.IT.Infrastructure.Department.DTO;

import com.example.IT.Infrastructure.Department.Enum.UserStatus;
import lombok.Data;

@Data
public class RegisterResponseDTO {
    private String username;
    private String email;
    private String phone;
    private String full_name;
    private String role_name;
    private UserStatus userStatus;
}
