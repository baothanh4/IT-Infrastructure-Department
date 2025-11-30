package com.example.IT.Infrastructure.Department.Service;

import com.example.IT.Infrastructure.Department.Config.JwtUtil;
import com.example.IT.Infrastructure.Department.DTO.JwtResponse;
import com.example.IT.Infrastructure.Department.DTO.LoginRequestDTO;
import com.example.IT.Infrastructure.Department.DTO.RegisterRequestDTO;
import com.example.IT.Infrastructure.Department.DTO.RegisterResponseDTO;
import com.example.IT.Infrastructure.Department.Enum.UserStatus;
import com.example.IT.Infrastructure.Department.Exception.InvalidCredentialsException;
import com.example.IT.Infrastructure.Department.Model.Role;
import com.example.IT.Infrastructure.Department.Model.Users;
import com.example.IT.Infrastructure.Department.Repository.RoleRepository;
import com.example.IT.Infrastructure.Department.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_TIME_DURATION = 15 * 60; // 15 phút tính bằng giây

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO dto) {
        if(userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username is already in use");
        }
        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        if(userRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Phone is already in use");
        }

        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setFull_name(dto.getFull_name());
        user.setStatus(UserStatus.ACTIVED);
        user.setAccount_non_locked(true);
        user.setFailed_attempts(0);

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        RegisterResponseDTO responseDTO = new RegisterResponseDTO();

        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPhone(user.getPhone());
        responseDTO.setFull_name(user.getFull_name());
        responseDTO.setRole_name(role.getName());
        responseDTO.setStatus(UserStatus.ACTIVED);
        userRepository.save(user);

        return responseDTO;

    }

    @Override
    public JwtResponse login(LoginRequestDTO dto) {

        Users user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Username or password is incorrect"));

        unlockAccountIfNeeded(user);

        if (!user.getAccount_non_locked()) {
            throw new InvalidCredentialsException("Account is locked. Try again later.");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            increaseFailedAttempts(user);
            throw new InvalidCredentialsException("Username or password is incorrect");
        }

        resetFailedAttempts(user);

        String accessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole().getName());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(accessToken);
        jwtResponse.setRefreshToken(refreshToken);
        return jwtResponse;
    }

    private void increaseFailedAttempts(Users user) {
        int newFailCount = user.getFailed_attempts() + 1;
        user.setFailed_attempts(newFailCount);

        if (newFailCount >= MAX_FAILED_ATTEMPTS) {
            user.setAccount_non_locked(false);
            user.setLock_time(LocalDateTime.now());
        }

        userRepository.save(user);
    }

    private void resetFailedAttempts(Users user) {
        if (user.getFailed_attempts() > 0) {
            user.setFailed_attempts(0);
            user.setAccount_non_locked(true);
            user.setLock_time(null);
            userRepository.save(user);
        }
    }

    private void unlockAccountIfNeeded(Users user) {
        if (!user.getAccount_non_locked() && user.getLock_time() != null) {
            LocalDateTime unlockTime = user.getLock_time().plusSeconds(LOCK_TIME_DURATION);
            if (LocalDateTime.now().isAfter(unlockTime)) {
                user.setAccount_non_locked(true);
                user.setFailed_attempts(0);
                user.setLock_time(null);
                userRepository.save(user);
            }
        }
    }
}
