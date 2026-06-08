package com.pasteleriaerp.controller;
import com.pasteleriaerp.service.CompraService; import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.util.*; import java.util.Map;
@RestController @RequestMapping("/api/compras") @RequiredArgsConstructor @CrossOrigin
public class CompraController {
    private final CompraService svc;
    @GetMapping public ResponseEntity<?> listar() { return ResponseEntity.ok(svc.listar()); }
    @GetMapping("/{id}") public ResponseEntity<?> get(@PathVariable Long id) { return ResponseEntity.ok(svc.buscarPorId(id)); }
    @PostMapping public ResponseEntity<?> crear(@RequestBody Map<String,Object> body) {
        try {
            Long pId = Long.valueOf(body.get("proveedorId").toString());
            String obs = body.getOrDefault("observaciones","").toString();
            List<Long>   prIds  = ((List<?>)body.get("productoIds")).stream().map(o->Long.valueOf(o.toString())).toList();
            List<Integer> cants = ((List<?>)body.get("cantidades")).stream().map(o->Integer.valueOf(o.toString())).toList();
            List<Double>  precs = ((List<?>)body.get("precios")).stream().map(o->Double.valueOf(o.toString())).toList();
            return ResponseEntity.ok(svc.crearCompra(pId,obs,prIds,cants,precs));
        } catch (Exception e) { return ResponseEntity.badRequest().body(Map.of("error",e.getMessage())); }
    }
    @PutMapping("/{id}/recibir") public ResponseEntity<?> recibir(@PathVariable Long id) {
        try { svc.recibir(id); return ResponseEntity.ok(Map.of("ok",true)); }
        catch (Exception e) { return ResponseEntity.badRequest().body(Map.of("error",e.getMessage())); }
    }
    @PutMapping("/{id}/cancelar") public ResponseEntity<?> cancelar(@PathVariable Long id) { svc.cancelar(id); return ResponseEntity.ok(Map.of("ok",true)); }
}
