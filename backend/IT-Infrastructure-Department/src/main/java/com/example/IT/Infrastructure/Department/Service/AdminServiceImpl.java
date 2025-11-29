package com.example.IT.Infrastructure.Department.Service;

import com.example.IT.Infrastructure.Department.DTO.UserResponseDTO;
import com.example.IT.Infrastructure.Department.Model.Role;
import com.example.IT.Infrastructure.Department.Model.Users;
import com.example.IT.Infrastructure.Department.Repository.RoleRepository;
import com.example.IT.Infrastructure.Department.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<Users> usersList = userRepository.findAll(); // Lấy tất cả user từ DB

        return usersList.stream().map(user -> {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setFull_name(user.getFull_name());
            dto.setStatus(user.getStatus());

            // Check role null để tránh NPE
            dto.setRole_name(user.getRole() != null ? user.getRole().getName() : "No Role Assigned");

            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

}
