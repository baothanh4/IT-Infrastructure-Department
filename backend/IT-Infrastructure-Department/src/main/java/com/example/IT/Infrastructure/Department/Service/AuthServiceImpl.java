package com.example.IT.Infrastructure.Department.Service;

import com.example.IT.Infrastructure.Department.DTO.UserRegisterDTO;
import com.example.IT.Infrastructure.Department.Enum.UserStatus;
import com.example.IT.Infrastructure.Department.Model.UserRole;
import com.example.IT.Infrastructure.Department.Model.Users;
import com.example.IT.Infrastructure.Department.Repository.RolesRepository;
import com.example.IT.Infrastructure.Department.Repository.UserRepository;
import com.example.IT.Infrastructure.Department.Repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public Users Register(UserRegisterDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username is already in use");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        if (userRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Phone is already in use");
        }

        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setFull_name(dto.getFull_name());
        user.setStatus(UserStatus.DEACTIVED);  // đợi admin kích hoạt
        user.setCreate_at(LocalDate.now());

        return userRepository.save(user);   // chỉ lưu user
    }

}
