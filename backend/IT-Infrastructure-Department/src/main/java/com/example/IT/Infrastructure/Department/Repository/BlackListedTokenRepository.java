package com.example.IT.Infrastructure.Department.Repository;

import com.example.IT.Infrastructure.Department.Model.BlackListedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListedTokenRepository extends JpaRepository<BlackListedToken,Long>{
    boolean existsByToken(String token);
}
