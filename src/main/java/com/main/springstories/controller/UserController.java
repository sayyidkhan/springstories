package com.main.springstories.controller;

import com.main.springstories.dto.RegisterDTO;
import com.main.springstories.models.UserEntity;
import com.main.springstories.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(
            CustomUserDetailsService customUserDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint, you have successfully logged in";
    }

    @GetMapping("/user_profile")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin_profile")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        if (customUserDetailsService.existsByUsername(registerDTO.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userEntity.setRole(registerDTO.getRole());

        customUserDetailsService.saveUser(userEntity);

        return new ResponseEntity<>("User registered successfully !", HttpStatus.OK);
    }

    @PostMapping("/multi_register")
    public ResponseEntity<String> register(@RequestBody List<RegisterDTO> registerDTOList) {
        List<String> errors = new ArrayList<>();

        for (RegisterDTO registerDTO : registerDTOList) {
            if (customUserDetailsService.existsByUsername(registerDTO.getUsername())) {
                errors.add("Username '" + registerDTO.getUsername() + "' is already taken.");
            } else {
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(registerDTO.getUsername());
                userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
                userEntity.setRole(registerDTO.getRole());

                customUserDetailsService.saveUser(userEntity);
            }
        }

        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Users registered successfully!", HttpStatus.OK);
        }
    }

}
