package com.example.cursomvc.resources;

import com.example.cursomvc.dto.EmailDTO;
import com.example.cursomvc.security.JWTUtil;
import com.example.cursomvc.security.UserSS;
import com.example.cursomvc.services.AuthService;
import com.example.cursomvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
        authService.sendNewPassword(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }
}
