package com.pasteleriaerp.controller;
import com.pasteleriaerp.service.DashboardService; import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/dashboard") @RequiredArgsConstructor
public class DashboardController {
    private final DashboardService svc;
    @GetMapping public ResponseEntity<?> get() { return ResponseEntity.ok(svc.getData()); }
}
