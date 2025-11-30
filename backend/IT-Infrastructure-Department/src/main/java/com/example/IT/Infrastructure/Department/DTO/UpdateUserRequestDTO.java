package com.example.IT.Infrastructure.Department.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequestDTO {
    @Size(max=100,message = "Full name must not be exceed 100 characters")
    private String full_name;
    @Size(min=6,max = 100,message = "Password must be between 6 and 100 characters")
    private String password;
    @Email(message = "Email must be valid")
    @Size(max=100,message = "Email must not be exceed 100 characters")
    private String email;
    @Pattern(
            regexp = "^(0\\d{9}|\\+84\\d{9})$",
            message = "Phone number must be a valid Vietnamese number"
    )
    private String phone;
    @Min(value = 1, message = "Role ID must be greater than 0")
    private Long role_id;
}
