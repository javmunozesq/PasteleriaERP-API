package com.pasteleriaerp.service;

import com.pasteleriaerp.model.*;
import com.pasteleriaerp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class IAService {

    private final VentaRepository           ventaRepo;
    private final CompraRepository          compraRepo;
    private final ProductoRepository        prodRepo;
    private final OrdenProduccionRepository ordenRepo;
    private final RestTemplate              restTemplate;

    @Value("${groq.api.key:}") private String groqKey;
    @Value("${groq.url}")      private String groqUrl;
    @Value("${groq.model}")    private String groqModel;

    // Cache: key=pregunta, value=[respuesta, timestamp]
    private final Map<String, Object[]> cache = new LinkedHashMap<>();
    private static final long CACHE_TTL_SECONDS = 120; // 2 minutos

    public String consultar(String pregunta) {
        String cacheKey = pregunta.trim().toLowerCase();

        // Devolver cache si existe y es reciente
        if (cache.containsKey(cacheKey)) {
            Object[] entry = cache.get(cacheKey);
            long age = Instant.now().getEpochSecond() - (long) entry[1];
            if (age < CACHE_TTL_SECONDS) {
                return (String) entry[0] + "\n\n_[Respuesta en cache - " + age + "s]_";
            }
        }

        String respuesta = callGroq(buildPrompt(buildContext(), pregunta));

        // Guardar en cache (maximo 20 entradas)
        if (cache.size() >= 20) {
            cache.remove(cache.keySet().iterator().next());
        }
        cache.put(cacheKey, new Object[]{respuesta, Instant.now().getEpochSecond()});

        return respuesta;
    }

    private String buildContext() {
        StringBuilder sb = new StringBuilder();

        List<Venta> ventas = ventaRepo.findAll().stream()
            .filter(v -> v.getEstado() == Venta.Estado.COMPLETADA)
            .collect(Collectors.toList());

        double totalVentas = ventas.stream().mapToDouble(Venta::getTotal).sum();
        sb.append(String.format("VENTAS: %d pedidos | %.2f EUR total\n\n",
            ventas.size(), totalVentas));

        Map<String, double[]> rank = new LinkedHashMap<>();
        for (Venta v : ventas) {
            for (VentaLinea vl : v.getLineas()) {
                String nom = vl.getProducto().getNombre();
                rank.computeIfAbsent(nom, k -> new double[]{0, 0})[0] += vl.getCantidad();
                rank.computeIfAbsent(nom, k -> new double[]{0, 0})[1] +=
                    vl.getCantidad() * vl.getPrecioUnitario();
            }
        }
        sb.append("PRODUCTOS MAS VENDIDOS:\n");
        rank.entrySet().stream()
            .sorted((a, b) -> Double.compare(b.getValue()[0], a.getValue()[0]))
            .limit(8)
            .forEach(e -> sb.append(String.format(
                "  - %s: %.0f ud | %.2f EUR\n", e.getKey(), e.getValue()[0], e.getValue()[1])));

        sb.append("\nSTOCK MATERIAS PRIMAS:\n");
        prodRepo.findByActivoTrueAndTipo(Producto.Tipo.MATERIA_PRIMA).forEach(p ->
            sb.append(String.format("  - %s: %.2f %s (min %.2f)%s\n",
                p.getNombre(), p.getStock(), p.getUnidadMedida(), p.getStockMinimo(),
                p.isStockBajo() ? " [CRITICO]" : "")));

        sb.append("\nPRODUCTOS TERMINADOS:\n");
        prodRepo.findByActivoTrueAndTipo(Producto.Tipo.PRODUCTO_TERMINADO).forEach(p ->
            sb.append(String.format("  - %s: %.0f ud (PVP %.2f EUR)\n",
                p.getNombre(), p.getStock(), p.getPrecioVenta())));

        ordenRepo.findTop10ByOrderByFechaDesc().forEach(o ->
            sb.append(String.format("PRODUCCION: %s x%d el %s\n",
                o.getReceta().getNombre(), o.getCantidad(), o.getFecha())));

        compraRepo.findTop10ByOrderByFechaDesc().forEach(c ->
            sb.append(String.format("COMPRA: %s %.2f EUR %s %s\n",
                c.getProveedor().getNombre(), c.getTotal(), c.getEstado(), c.getFecha())));

        return sb.toString();
    }

    private String buildPrompt(String ctx, String pregunta) {
        return "Eres el asistente IA del Obrador San Rafael, pasteleria artesanal de Cordoba. "
            + "Analiza datos reales y da recomendaciones concretas.\n\n"
            + "DATOS:\n" + ctx
            + "\nPREGUNTA: " + pregunta
            + "\n\nResponde en espanol con emojis. Maximo 300 palabras. Se directo y especifico.";
    }

    @SuppressWarnings("unchecked")
    private String callGroq(String prompt) {
        if (groqKey == null || groqKey.isBlank() || groqKey.startsWith("PON_")) {
            return "Configura groq.api.key en application.properties. "
                + "Key gratuita en: https://console.groq.com/keys";
        }
        try {
            HttpHeaders h = new HttpHeaders();
            h.setContentType(MediaType.APPLICATION_JSON);
            h.setBearerAuth(groqKey);

            Map<String, Object> body = Map.of(
                "model",    groqModel,
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "max_tokens", 800,
                "temperature", 0.7
            );

            ResponseEntity<Map> resp = restTemplate.postForEntity(
                groqUrl, new HttpEntity<>(body, h), Map.class);

            List<Map> choices = (List<Map>) resp.getBody().get("choices");
            Map msg = (Map) choices.get(0).get("message");
            return (String) msg.get("content");

        } catch (Exception e) {
            return "Error Groq: " + e.getMessage();
        }
    }
}
