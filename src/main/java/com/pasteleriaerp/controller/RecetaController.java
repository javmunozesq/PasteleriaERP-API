package com.pasteleriaerp.controller;
import com.pasteleriaerp.service.RecetaService; import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.util.*; import java.util.Map;
@RestController @RequestMapping("/api/recetas") @RequiredArgsConstructor @CrossOrigin
public class RecetaController {
    private final RecetaService svc;
    @GetMapping public ResponseEntity<?> listar() { return ResponseEntity.ok(svc.listar()); }
    @GetMapping("/{id}") public ResponseEntity<?> get(@PathVariable Long id) { return ResponseEntity.ok(svc.buscarPorId(id)); }
    @PostMapping public ResponseEntity<?> crear(@RequestBody Map<String,Object> body) {
        try {
            Long pfId = Long.valueOf(body.get("productoFinalId").toString());
            String nombre = body.get("nombre").toString();
            String desc = body.getOrDefault("descripcion","").toString();
            List<Long> ingIds = ((List<?>)body.getOrDefault("ingIds",List.of())).stream().map(o->Long.valueOf(o.toString())).toList();
            List<Double> cants = ((List<?>)body.getOrDefault("cantidades",List.of())).stream().map(o->Double.valueOf(o.toString())).toList();
            return ResponseEntity.ok(svc.guardar(pfId,nombre,desc,ingIds,cants));
        } catch (Exception e) { return ResponseEntity.badRequest().body(Map.of("error",e.getMessage())); }
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> eliminar(@PathVariable Long id) { svc.eliminar(id); return ResponseEntity.ok(Map.of("ok",true)); }
}
