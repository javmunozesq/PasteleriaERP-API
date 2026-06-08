package com.pasteleriaerp.service;
import com.pasteleriaerp.model.*; import com.pasteleriaerp.repository.*;
import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; import java.time.LocalDate; import java.util.List;
@Service @RequiredArgsConstructor public class CompraService {
    private final CompraRepository compraRepo; private final ProveedorRepository provRepo;
    private final ProductoRepository prodRepo; private final MovimientoStockRepository movRepo;
    public List<Compra> listar() { return compraRepo.findAll(); }
    public Compra buscarPorId(Long id) { return compraRepo.findById(id).orElseThrow(); }
    @Transactional
    public Compra crearCompra(Long provId, String obs, List<Long> pIds, List<Integer> cants, List<Double> precios) {
        if (pIds==null||pIds.isEmpty()) throw new RuntimeException("Añade al menos un producto.");
        Compra c=new Compra(); c.setProveedor(provRepo.findById(provId).orElseThrow());
        c.setFecha(LocalDate.now()); c.setObservaciones(obs); c.setEstado(Compra.Estado.PENDIENTE);
        double total=0;
        for (int i=0; i<pIds.size(); i++) {
            if (pIds.get(i)==null) continue;
            Producto p=prodRepo.findById(pIds.get(i)).orElseThrow(); int cant=cants.get(i); double precio=precios.get(i);
            CompraLinea cl=new CompraLinea(); cl.setCompra(c); cl.setProducto(p); cl.setCantidad(cant); cl.setPrecioUnitario(precio);
            c.getLineas().add(cl); total+=precio*cant;
        }
        c.setTotal(total); return compraRepo.save(c);
    }
    @Transactional public void recibir(Long id) {
        Compra c=buscarPorId(id); if (c.getEstado()!=Compra.Estado.PENDIENTE) throw new RuntimeException("No está pendiente.");
        for (CompraLinea cl:c.getLineas()) { Producto p=cl.getProducto(); p.setStock(p.getStock()+cl.getCantidad()); prodRepo.save(p);
            MovimientoStock m=new MovimientoStock(); m.setProducto(p); m.setTipo(MovimientoStock.Tipo.ENTRADA_COMPRA); m.setCantidad(cl.getCantidad()); m.setFecha(LocalDate.now()); movRepo.save(m); }
        c.setEstado(Compra.Estado.RECIBIDA); compraRepo.save(c);
    }
    @Transactional public void cancelar(Long id) { Compra c=buscarPorId(id); c.setEstado(Compra.Estado.CANCELADA); compraRepo.save(c); }
}
