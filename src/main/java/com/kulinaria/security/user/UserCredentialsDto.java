package com.kulinaria.security.user;

import java.util.Set;

public record UserCredentialsDto(String email, String password, Set<String> roles) {}
