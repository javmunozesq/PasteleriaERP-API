package com.pasteleriaerp.service;
import com.pasteleriaerp.model.Cliente; import com.pasteleriaerp.repository.ClienteRepository;
import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Service;
import java.util.List;
@Service @RequiredArgsConstructor public class ClienteService {
    private final ClienteRepository repo;
    public List<Cliente> listar() { return repo.findByActivoTrue(); }
    public Cliente buscarPorId(Long id) { return repo.findById(id).orElseThrow(); }
    public Cliente guardar(Cliente c) { return repo.save(c); }
    public void eliminar(Long id) { Cliente c=buscarPorId(id); c.setActivo(false); repo.save(c); }
}
