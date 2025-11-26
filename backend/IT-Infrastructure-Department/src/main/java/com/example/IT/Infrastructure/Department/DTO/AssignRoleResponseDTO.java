package com.example.IT.Infrastructure.Department.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignRoleResponseDTO {
    private Long userId;
    private String userName;
    private Long roleId;
    private String roleName;
    private LocalDate assignedAt;
}

