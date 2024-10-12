package com.kulinaria.security.config;

import com.kulinaria.security.user.UserCredentialsDto;
import com.kulinaria.security.user.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findCredentialsByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with email %s not found".formatted(username)));
    }

    private UserDetails createUserDetails(UserCredentialsDto credentials) {
        return User.builder()
                .username(credentials.email())
                .password(credentials.password())
                .roles(credentials.roles().toArray(String[]::new))
                .build();
    }
}
