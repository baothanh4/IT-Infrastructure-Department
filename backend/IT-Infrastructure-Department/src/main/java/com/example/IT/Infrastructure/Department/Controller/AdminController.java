package com.example.IT.Infrastructure.Department.Controller;


import com.example.IT.Infrastructure.Department.DTO.AssignRoleDTO;
import com.example.IT.Infrastructure.Department.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/assign-role")
    public ResponseEntity<?> assignRole(@RequestBody AssignRoleDTO dto){
        return ResponseEntity.ok().body(adminService.assignRole(dto));
    }
}
