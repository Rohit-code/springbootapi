package com.springbootapi.service;

import com.springbootapi.payload.LoginDto;
import com.springbootapi.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
