package com.example.IT.Infrastructure.Department.Service;

import com.example.IT.Infrastructure.Department.Model.Users;
import com.example.IT.Infrastructure.Department.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Users user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
        return new CustomUserDetails(user);
    }
}
