package com.pasteleriaerp.controller;
import com.pasteleriaerp.dto.LoginRequest; import com.pasteleriaerp.security.JwtUtil;
import lombok.RequiredArgsConstructor; import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*; import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager; private final JwtUtil jwtUtil;
    @PostMapping("/login") public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            String token = jwtUtil.generateToken(req.getUsername());
            return ResponseEntity.ok(Map.of("token",token,"username",req.getUsername()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(Map.of("error","Credenciales incorrectas"));
        }
    }
}
