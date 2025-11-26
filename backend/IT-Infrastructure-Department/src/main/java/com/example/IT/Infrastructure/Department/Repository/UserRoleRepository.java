package com.example.IT.Infrastructure.Department.Repository;

import com.example.IT.Infrastructure.Department.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long>{
    boolean existsByUser_IdAndRole_Id(Long userId, Long roleId);
}
