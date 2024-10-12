package com.kulinaria.security.user;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Mapper {

    public UserCredentialsDto map(User user) {
        Set<String> roles = user.getRoles().stream()
                .map(UserRole::getName)
                .collect(Collectors.toSet());
        return new UserCredentialsDto(user.getEmail(), user.getPassword(), roles);
    }
}
