package com.pasteleriaerp.service;
import com.pasteleriaerp.model.*; import com.pasteleriaerp.repository.*;
import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Service;
import java.util.*; import java.util.stream.Collectors;
@Service @RequiredArgsConstructor public class DashboardService {
    private final VentaRepository ventaRepo; private final ClienteRepository clienteRepo;
    private final ProductoRepository prodRepo; private final OrdenProduccionRepository ordenRepo;
    public Map<String,Object> getData() {
        Map<String,Object> d = new LinkedHashMap<>();
        List<Venta> ventas = ventaRepo.findAll();
        d.put("totalVentas", Math.round(ventas.stream().mapToDouble(Venta::getTotal).sum()*100.0)/100.0);
        d.put("numVentas", ventas.size());
        d.put("numClientes", clienteRepo.findByActivoTrue().size());
        d.put("numProductos", prodRepo.findByActivoTrue().size());
        d.put("stockBajo", prodRepo.findByActivoTrue().stream().filter(Producto::isStockBajo).collect(Collectors.toList()));
        d.put("ultimasVentas", ventaRepo.findTop10ByOrderByFechaDesc());
        d.put("ultimasOrdenes", ordenRepo.findTop10ByOrderByFechaDesc());
        return d;
    }
}
