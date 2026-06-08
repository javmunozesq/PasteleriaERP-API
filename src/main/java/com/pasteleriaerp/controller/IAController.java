package com.pasteleriaerp.controller;

import com.pasteleriaerp.service.IAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
@CrossOrigin
public class IAController {

    private final IAService iaService;

    /** Consulta libre: el usuario escribe su pregunta */
    @PostMapping("/consulta")
    public ResponseEntity<?> consulta(@RequestBody Map<String,String> body) {
        String pregunta = body.getOrDefault("pregunta", "Dame un análisis completo del negocio.");
        try {
            return ResponseEntity.ok(Map.of("respuesta", iaService.consultar(pregunta)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    /** Análisis automático completo (botón rápido) */
    @GetMapping("/analisis-completo")
    public ResponseEntity<?> analisisCompleto() {
        String pregunta =
            "Realiza un informe ejecutivo completo con estas secciones:\n" +
            "1. 📊 RESUMEN DEL NEGOCIO: estado actual en 2-3 frases\n" +
            "2. 🏆 PRODUCTOS ESTRELLA: los más vendidos y por qué son importantes\n" +
            "3. ⚠️ ALERTAS CRÍTICAS: stock bajo que puede paralizar la producción\n" +
            "4. 🛒 COMPRAS RECOMENDADAS: qué comprar, a qué proveedor y cuánto, basándote en el ritmo de ventas\n" +
            "5. 🧁 PRODUCCIÓN SUGERIDA: qué fabricar esta semana según la demanda\n" +
            "6. 🎯 ACCIÓN PRIORITARIA: la única cosa más importante que hacer hoy";
        try {
            return ResponseEntity.ok(Map.of("respuesta", iaService.consultar(pregunta)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    /** Recomendación de compras urgentes */
    @GetMapping("/compras-recomendadas")
    public ResponseEntity<?> comprasRecomendadas() {
        String pregunta =
            "Analiza el stock actual y las ventas históricas. Dime exactamente:\n" +
            "- Qué productos debo comprar AHORA (stock crítico)\n" +
            "- Cuántas unidades/kg/litros de cada uno\n" +
            "- A qué proveedor comprarlo si aparece en el historial\n" +
            "- Cuánto me va a costar aproximadamente\n" +
            "Ordena por urgencia (más urgente primero).";
        try {
            return ResponseEntity.ok(Map.of("respuesta", iaService.consultar(pregunta)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    /** Predicción de demanda */
    @GetMapping("/prediccion-demanda")
    public ResponseEntity<?> prediccionDemanda() {
        String pregunta =
            "Basándote en el historial de ventas por producto:\n" +
            "- ¿Qué productos tienen mayor demanda?\n" +
            "- ¿Qué cantidad debería tener en stock para la próxima semana?\n" +
            "- ¿Hay algún producto con baja rotación que debería reducir?\n" +
            "- Dame una estimación de ingresos para los próximos 7 días si mantenemos el ritmo actual.";
        try {
            return ResponseEntity.ok(Map.of("respuesta", iaService.consultar(pregunta)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
