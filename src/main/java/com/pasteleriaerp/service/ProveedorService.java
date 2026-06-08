package com.pasteleriaerp.service;
import com.pasteleriaerp.model.Proveedor; import com.pasteleriaerp.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Service; import java.util.List;
@Service @RequiredArgsConstructor public class ProveedorService {
    private final ProveedorRepository repo;
    public List<Proveedor> listar() { return repo.findByActivoTrue(); }
    public Proveedor buscarPorId(Long id) { return repo.findById(id).orElseThrow(); }
    public Proveedor guardar(Proveedor p) { return repo.save(p); }
    public void eliminar(Long id) { Proveedor p=buscarPorId(id); p.setActivo(false); repo.save(p); }
}
