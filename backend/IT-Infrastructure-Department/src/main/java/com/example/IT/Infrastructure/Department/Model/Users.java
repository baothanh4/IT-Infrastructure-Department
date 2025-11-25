package com.example.IT.Infrastructure.Department.Model;


import com.example.IT.Infrastructure.Department.Enum.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username must not empty")
    private String username;

    @NotBlank(message = "Password must not empty")
    private String password;

    @NotBlank(message = "Email must not empty")
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDate create_at;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<UserRole> roles=new HashSet<>();
}
