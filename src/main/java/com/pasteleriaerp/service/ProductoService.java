package com.pasteleriaerp.service;
import com.pasteleriaerp.model.*; import com.pasteleriaerp.repository.*;
import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Service; import java.util.List;
@Service @RequiredArgsConstructor public class ProductoService {
    private final ProductoRepository repo; private final CategoriaRepository catRepo;
    public List<Producto> listar() { return repo.findByActivoTrue(); }
    public List<Producto> listarTerminados() { return repo.findByActivoTrueAndTipo(Producto.Tipo.PRODUCTO_TERMINADO); }
    public List<Producto> listarMateriasPrimas() { return repo.findByActivoTrueAndTipo(Producto.Tipo.MATERIA_PRIMA); }
    public List<Categoria> listarCategorias() { return catRepo.findAll(); }
    public Producto buscarPorId(Long id) { return repo.findById(id).orElseThrow(); }
    public Producto guardar(Producto p) { return repo.save(p); }
    public void eliminar(Long id) { Producto p=buscarPorId(id); p.setActivo(false); repo.save(p); }
}
