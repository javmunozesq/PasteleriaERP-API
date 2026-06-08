package com.pasteleriaerp.service;
import com.pasteleriaerp.model.*; import com.pasteleriaerp.repository.*;
import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; import java.time.LocalDate; import java.util.List;
@Service @RequiredArgsConstructor public class VentaService {
    private final VentaRepository ventaRepo; private final ClienteRepository clienteRepo;
    private final ProductoRepository prodRepo; private final MovimientoStockRepository movRepo;
    public List<Venta> listar() { return ventaRepo.findAll(); }
    public Venta buscarPorId(Long id) { return ventaRepo.findById(id).orElseThrow(); }
    @Transactional
    public Venta crearVenta(Long clienteId, String obs, List<Long> pIds, List<Integer> cants, List<Double> precios) {
        if (pIds==null||pIds.isEmpty()) throw new RuntimeException("Añade al menos un producto.");
        Venta v = new Venta(); v.setCliente(clienteRepo.findById(clienteId).orElseThrow());
        v.setFecha(LocalDate.now()); v.setObservaciones(obs); v.setEstado(Venta.Estado.COMPLETADA);
        double total=0;
        for (int i=0; i<pIds.size(); i++) {
            if (pIds.get(i)==null) continue;
            Producto p=prodRepo.findById(pIds.get(i)).orElseThrow();
            int cant=cants.get(i); double precio=precios.get(i);
            if (p.getStock()<cant) throw new RuntimeException("Stock insuficiente: "+p.getNombre()+" (disponible: "+p.getStock()+")");
            p.setStock(p.getStock()-cant); prodRepo.save(p);
            MovimientoStock m=new MovimientoStock(); m.setProducto(p); m.setTipo(MovimientoStock.Tipo.SALIDA_VENTA); m.setCantidad(-cant); m.setFecha(LocalDate.now()); movRepo.save(m);
            VentaLinea vl=new VentaLinea(); vl.setVenta(v); vl.setProducto(p); vl.setCantidad(cant); vl.setPrecioUnitario(precio); vl.setIva(10.0);
            v.getLineas().add(vl); total+=precio*cant*1.10;
        }
        v.setTotal(total); return ventaRepo.save(v);
    }
    @Transactional public void cancelar(Long id) {
        Venta v=buscarPorId(id); if (v.getEstado()==Venta.Estado.CANCELADA) return;
        for (VentaLinea vl:v.getLineas()) { Producto p=vl.getProducto(); p.setStock(p.getStock()+vl.getCantidad()); prodRepo.save(p); }
        v.setEstado(Venta.Estado.CANCELADA); ventaRepo.save(v);
    }
}
