package com.pasteleriaerp.controller;
import com.pasteleriaerp.service.ProduccionService; import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/produccion") @RequiredArgsConstructor @CrossOrigin
public class ProduccionController {
    private final ProduccionService svc;
    @GetMapping public ResponseEntity<?> listar() { return ResponseEntity.ok(svc.listar()); }
    @PostMapping public ResponseEntity<?> ejecutar(@RequestBody Map<String,Object> body) {
        try {
            Long recetaId = Long.valueOf(body.get("recetaId").toString());
            int cantidad  = Integer.parseInt(body.get("cantidad").toString());
            String obs    = body.getOrDefault("observaciones","").toString();
            return ResponseEntity.ok(svc.ejecutar(recetaId,cantidad,obs));
        } catch (Exception e) { return ResponseEntity.badRequest().body(Map.of("error",e.getMessage())); }
    }
}
