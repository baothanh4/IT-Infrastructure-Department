package com.example.IT.Infrastructure.Department.Controller;


import com.example.IT.Infrastructure.Department.DTO.RegisterRequestDTO;
import com.example.IT.Infrastructure.Department.Service.AdminService;
import com.example.IT.Infrastructure.Department.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody RegisterRequestDTO dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles(){
        return  ResponseEntity.ok(adminService.getAllRoles());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(adminService.deleteUser(id));
    }

}
