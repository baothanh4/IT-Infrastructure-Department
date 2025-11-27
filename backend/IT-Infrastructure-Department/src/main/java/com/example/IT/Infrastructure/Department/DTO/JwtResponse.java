package com.example.IT.Infrastructure.Department.DTO;

import lombok.Data;

@Data
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType="Bearer";
}
