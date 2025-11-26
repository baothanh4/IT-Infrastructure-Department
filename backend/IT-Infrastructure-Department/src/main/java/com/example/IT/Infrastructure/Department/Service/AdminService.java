package com.example.IT.Infrastructure.Department.Service;

import com.example.IT.Infrastructure.Department.DTO.AssignRoleDTO;
import com.example.IT.Infrastructure.Department.DTO.AssignRoleResponseDTO;
import com.example.IT.Infrastructure.Department.Model.UserRole;

public interface AdminService {
    AssignRoleResponseDTO assignRole(AssignRoleDTO dto);
}
