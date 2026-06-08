package com.pasteleriaerp.controller;
import com.pasteleriaerp.model.Producto; import com.pasteleriaerp.service.ProductoService;
import lombok.RequiredArgsConstructor; import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/productos") @RequiredArgsConstructor @CrossOrigin
public class ProductoController {
    private final ProductoService svc;
    @GetMapping public ResponseEntity<?> listar() { return ResponseEntity.ok(svc.listar()); }
    @GetMapping("/materias-primas") public ResponseEntity<?> mps() { return ResponseEntity.ok(svc.listarMateriasPrimas()); }
    @GetMapping("/terminados") public ResponseEntity<?> terminados() { return ResponseEntity.ok(svc.listarTerminados()); }
    @GetMapping("/categorias") public ResponseEntity<?> cats() { return ResponseEntity.ok(svc.listarCategorias()); }
    @GetMapping("/{id}") public ResponseEntity<?> get(@PathVariable Long id) { return ResponseEntity.ok(svc.buscarPorId(id)); }
    @PostMapping public ResponseEntity<?> crear(@RequestBody Producto p) {
        if (p.getNombre()==null||p.getNombre().isBlank()) return ResponseEntity.badRequest().body(Map.of("error","El nombre es obligatorio"));
        return ResponseEntity.ok(svc.guardar(p));
    }
    @PutMapping("/{id}") public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Producto p) { p.setId(id); return ResponseEntity.ok(svc.guardar(p)); }
    @DeleteMapping("/{id}") public ResponseEntity<?> eliminar(@PathVariable Long id) { svc.eliminar(id); return ResponseEntity.ok(Map.of("ok",true)); }
}
