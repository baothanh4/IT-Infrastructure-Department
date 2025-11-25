package com.example.IT.Infrastructure.Department.Service;

import com.example.IT.Infrastructure.Department.Model.Roles;
import com.example.IT.Infrastructure.Department.Repository.RolesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {
    private final RolesRepository rolesRepository;

    public RolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<Roles> getAllRoles(){
        return rolesRepository.findAll();
    }
}
