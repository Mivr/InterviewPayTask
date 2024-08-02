package com.example.emerchantpaytask.security;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.emerchantpaytask.security.repository.User;
import com.example.emerchantpaytask.security.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    // TODO this is only for demonstration purposes, in a real application this should be stored in a database
    private final User adminUser = new User(0L, "admin", new BCryptPasswordEncoder().encode("password"));

    CustomUserDetailsService(
        @Autowired UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (Objects.equals(username, adminUser.username())) {
            return new org.springframework.security.core.userdetails.User(
                adminUser.username(),
                adminUser.password(),
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
            user.username(),
            user.password(),
            List.of(new SimpleGrantedAuthority("ROLE_MERCHANT"))
        );
    }

    public void createUser(User newUser) {
        userRepository.save(new User(null, newUser.username(), new BCryptPasswordEncoder().encode(newUser.password())));
    }
}