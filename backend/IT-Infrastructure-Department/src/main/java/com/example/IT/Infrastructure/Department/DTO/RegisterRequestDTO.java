package com.example.IT.Infrastructure.Department.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "Username must not be empty")
    private String username;
    @NotBlank(message = "Password must not be empty")
    private String password;
    @NotBlank(message = "Email must not be empty")
    private String email;
    @NotBlank(message = "Phone must not be empty")
    private String phone;
    @NotBlank(message = "Full name must not be empty")
    private String full_name;
    private Long roleId;
}
