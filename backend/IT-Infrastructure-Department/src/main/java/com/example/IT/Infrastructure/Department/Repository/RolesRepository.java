package com.example.IT.Infrastructure.Department.Repository;

import com.example.IT.Infrastructure.Department.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
}
