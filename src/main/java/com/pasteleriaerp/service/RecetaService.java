package com.pasteleriaerp.service;
import com.pasteleriaerp.model.*; import com.pasteleriaerp.repository.*;
import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; import java.util.*;
@Service @RequiredArgsConstructor public class RecetaService {
    private final RecetaRepository repo; private final ProductoRepository prodRepo;
    public List<Receta> listar() { return repo.findAll(); }
    public Receta buscarPorId(Long id) { return repo.findById(id).orElseThrow(); }
    @Transactional
    public Receta guardar(Long prodFinalId, String nombre, String desc, List<Long> ingIds, List<Double> cants) {
        Receta r = new Receta(); r.setNombre(nombre); r.setDescripcion(desc);
        r.setProductoFinal(prodRepo.findById(prodFinalId).orElseThrow());
        List<RecetaIngrediente> lista = new ArrayList<>();
        if (ingIds != null) for (int i=0; i<ingIds.size(); i++) {
            if (ingIds.get(i)==null) continue;
            RecetaIngrediente ri = new RecetaIngrediente();
            ri.setReceta(r); ri.setIngrediente(prodRepo.findById(ingIds.get(i)).orElseThrow()); ri.setCantidad(cants.get(i)); lista.add(ri);
        }
        r.setIngredientes(lista); return repo.save(r);
    }
    public void eliminar(Long id) { repo.deleteById(id); }
}
