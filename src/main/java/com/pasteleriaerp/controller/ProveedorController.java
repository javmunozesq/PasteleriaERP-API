package com.pasteleriaerp.controller;
import com.pasteleriaerp.model.Proveedor; import com.pasteleriaerp.service.ProveedorService;
import lombok.RequiredArgsConstructor; import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/proveedores") @RequiredArgsConstructor @CrossOrigin
public class ProveedorController {
    private final ProveedorService svc;
    @GetMapping public ResponseEntity<?> listar() { return ResponseEntity.ok(svc.listar()); }
    @GetMapping("/{id}") public ResponseEntity<?> get(@PathVariable Long id) { return ResponseEntity.ok(svc.buscarPorId(id)); }
    @PostMapping public ResponseEntity<?> crear(@RequestBody Proveedor p) {
        if (p.getNombre()==null||p.getNombre().isBlank()) return ResponseEntity.badRequest().body(Map.of("error","El nombre es obligatorio"));
        return ResponseEntity.ok(svc.guardar(p));
    }
    @PutMapping("/{id}") public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Proveedor p) { p.setId(id); return ResponseEntity.ok(svc.guardar(p)); }
    @DeleteMapping("/{id}") public ResponseEntity<?> eliminar(@PathVariable Long id) { svc.eliminar(id); return ResponseEntity.ok(Map.of("ok",true)); }
}
