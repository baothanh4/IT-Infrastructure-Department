package com.example.IT.Infrastructure.Department.Controller;

import com.example.IT.Infrastructure.Department.Config.JwtUtil;
import com.example.IT.Infrastructure.Department.DTO.JwtResponse;
import com.example.IT.Infrastructure.Department.DTO.LoginRequestDTO;
import com.example.IT.Infrastructure.Department.DTO.RegisterRequestDTO;
import com.example.IT.Infrastructure.Department.Model.BlackListedToken;
import com.example.IT.Infrastructure.Department.Repository.BlackListedTokenRepository;
import com.example.IT.Infrastructure.Department.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private BlackListedTokenRepository blackListedTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequestDTO dto) {
        JwtResponse jwtResponse = authService.login(dto);
        return ResponseEntity.ok().body(jwtResponse);
    }
//
//    @PostMapping("/refresh-token")
//    public ResponseEntity<?> refreshToken(@RequestBody Map<String,String> body){
//        String refreshToken = body.get("refresh-token");
//
//    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader=request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid Authorization Token");
        }

        String token=authHeader.substring(7);

        BlackListedToken blackListed=new  BlackListedToken();
        blackListed.setToken(token);
        blackListed.setExpired_at(new Date(System.currentTimeMillis()+3600*1000));
        blackListedTokenRepository.save(blackListed);
        return ResponseEntity.ok("Logged out successfully");
    }
}
