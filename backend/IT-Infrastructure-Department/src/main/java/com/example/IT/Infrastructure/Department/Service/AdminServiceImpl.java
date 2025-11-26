package com.example.IT.Infrastructure.Department.Service;


import com.example.IT.Infrastructure.Department.DTO.AssignRoleDTO;
import com.example.IT.Infrastructure.Department.DTO.AssignRoleResponseDTO;
import com.example.IT.Infrastructure.Department.Enum.UserStatus;
import com.example.IT.Infrastructure.Department.Model.Roles;
import com.example.IT.Infrastructure.Department.Model.UserRole;
import com.example.IT.Infrastructure.Department.Model.Users;
import com.example.IT.Infrastructure.Department.Repository.RolesRepository;
import com.example.IT.Infrastructure.Department.Repository.UserRepository;
import com.example.IT.Infrastructure.Department.Repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public AssignRoleResponseDTO assignRole(AssignRoleDTO dto) {
        // 1. Kiểm tra user
        Users users = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Kiểm tra role
        Roles roles = rolesRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 3. Kiểm tra user-role đã tồn tại hay chưa
        boolean exists = userRoleRepository.existsByUser_IdAndRole_Id(dto.getUserId(), dto.getRoleId());
        if (exists) {
            throw new RuntimeException("User already assigned to this role!");
        }

        // 4. Tạo mới UserRole
        UserRole userRole = new UserRole();
        userRole.setUser(users);
        userRole.setRole(roles);
        userRole.setAssigned_at(LocalDate.now());

        users.setStatus(UserStatus.ACTIVED);
        userRepository.save(users);
        userRoleRepository.save(userRole);

        // 5. Trả về DTO
        AssignRoleResponseDTO res = new AssignRoleResponseDTO();
        res.setUserId(users.getId());
        res.setUserName(users.getUsername());
        res.setRoleId(roles.getId());
        res.setRoleName(roles.getName());
        res.setAssignedAt(userRole.getAssigned_at());

        return res;
    }

}
