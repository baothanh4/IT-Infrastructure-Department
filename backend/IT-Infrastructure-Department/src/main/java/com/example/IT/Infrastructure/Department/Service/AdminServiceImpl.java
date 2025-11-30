package com.example.IT.Infrastructure.Department.Service;

import com.example.IT.Infrastructure.Department.DTO.UpdateUserRequestDTO;
import com.example.IT.Infrastructure.Department.DTO.UserResponseDTO;
import com.example.IT.Infrastructure.Department.Model.Role;
import com.example.IT.Infrastructure.Department.Model.Users;
import com.example.IT.Infrastructure.Department.Repository.RoleRepository;
import com.example.IT.Infrastructure.Department.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


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

    @Override
    public String deleteUser(Long id){
        userRepository.deleteById(id);
        return "Delete user complete";
    }

    @Override
    public void updateUser(Long id, UpdateUserRequestDTO dto) {

        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Cập nhật từng field nếu không null
        if (dto.getFull_name() != null && !dto.getFull_name().isEmpty()) {
            user.setFull_name(dto.getFull_name());
        }

        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
            user.setPhone(dto.getPhone());
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRole_id() != null) {
            Role role = roleRepository.findById(dto.getRole_id())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
        }

        userRepository.save(user);
    }


}
