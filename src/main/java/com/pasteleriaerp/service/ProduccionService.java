package com.pasteleriaerp.service;
import com.pasteleriaerp.model.*; import com.pasteleriaerp.repository.*;
import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; import java.time.LocalDate; import java.util.List;
@Service @RequiredArgsConstructor public class ProduccionService {
    private final OrdenProduccionRepository ordenRepo; private final RecetaRepository recetaRepo;
    private final ProductoRepository prodRepo; private final MovimientoStockRepository movRepo;
    public List<OrdenProduccion> listar() { return ordenRepo.findAll(); }
    @Transactional public OrdenProduccion ejecutar(Long recetaId, int cantidad, String obs) {
        Receta receta=recetaRepo.findById(recetaId).orElseThrow();
        for (RecetaIngrediente ri:receta.getIngredientes()) {
            double needed=ri.getCantidad()*cantidad;
            if (ri.getIngrediente().getStock()<needed) throw new RuntimeException(
                "Stock insuficiente de '"+ri.getIngrediente().getNombre()+"'. Necesario: "+String.format("%.2f",needed)+" | Disponible: "+String.format("%.2f",ri.getIngrediente().getStock()));
        }
        for (RecetaIngrediente ri:receta.getIngredientes()) {
            double consumed=ri.getCantidad()*cantidad; Producto p=ri.getIngrediente(); p.setStock(p.getStock()-consumed); prodRepo.save(p);
            MovimientoStock m=new MovimientoStock(); m.setProducto(p); m.setTipo(MovimientoStock.Tipo.CONSUMO_PRODUCCION); m.setCantidad(-consumed); m.setFecha(LocalDate.now()); movRepo.save(m);
        }
        Producto pf=receta.getProductoFinal(); pf.setStock(pf.getStock()+cantidad); prodRepo.save(pf);
        MovimientoStock mf=new MovimientoStock(); mf.setProducto(pf); mf.setTipo(MovimientoStock.Tipo.PRODUCCION); mf.setCantidad(cantidad); mf.setFecha(LocalDate.now()); movRepo.save(mf);
        OrdenProduccion o=new OrdenProduccion(); o.setReceta(receta); o.setCantidad(cantidad); o.setFecha(LocalDate.now()); o.setEstado(OrdenProduccion.Estado.COMPLETADA); o.setObservaciones(obs);
        return ordenRepo.save(o);
    }
}
