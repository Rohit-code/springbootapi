package com.springbootapi.service.impl;

import com.springbootapi.entity.User;
import com.springbootapi.exception.BlogAPIException;
import com.springbootapi.payload.LoginDto;
import com.springbootapi.payload.RegisterDto;
import com.springbootapi.repository.RoleRepository;
import com.springbootapi.repository.UserRepository;
import com.springbootapi.security.JwtTokenProvider;
import com.springbootapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.springbootapi.entity.Role;

import java.util.HashSet;
import java.util.Set;


@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        //check for username exist in db
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Username Exists in Database");
        }
        //check for email exist
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Email Exists in Database");
        }
        User user = new User();
        user.setFullname(registerDto.getFullname());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("Role_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return "User Registration Success inka Login loki Dengey";
    }
}
