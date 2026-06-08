package com.pasteleriaerp.controller;
import com.pasteleriaerp.model.Cliente; import com.pasteleriaerp.service.ClienteService;
import lombok.RequiredArgsConstructor; import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/clientes") @RequiredArgsConstructor @CrossOrigin
public class ClienteController {
    private final ClienteService svc;
    @GetMapping public ResponseEntity<?> listar() { return ResponseEntity.ok(svc.listar()); }
    @GetMapping("/{id}") public ResponseEntity<?> get(@PathVariable Long id) { return ResponseEntity.ok(svc.buscarPorId(id)); }
    @PostMapping public ResponseEntity<?> crear(@RequestBody Cliente c) {
        if (c.getNombre()==null||c.getNombre().isBlank()) return ResponseEntity.badRequest().body(Map.of("error","El nombre es obligatorio"));
        return ResponseEntity.ok(svc.guardar(c));
    }
    @PutMapping("/{id}") public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cliente c) {
        c.setId(id); if (c.getNombre()==null||c.getNombre().isBlank()) return ResponseEntity.badRequest().body(Map.of("error","El nombre es obligatorio"));
        return ResponseEntity.ok(svc.guardar(c));
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> eliminar(@PathVariable Long id) { svc.eliminar(id); return ResponseEntity.ok(Map.of("ok",true)); }
}
