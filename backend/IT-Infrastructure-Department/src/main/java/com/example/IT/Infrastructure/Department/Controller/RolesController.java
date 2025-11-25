package com.example.IT.Infrastructure.Department.Controller;

import com.example.IT.Infrastructure.Department.Model.Roles;
import com.example.IT.Infrastructure.Department.Service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @GetMapping
    public ResponseEntity<List<Roles>> getAllRoles(){
        return ResponseEntity.ok().body(rolesService.getAllRoles());
    }
}
