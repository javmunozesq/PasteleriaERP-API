package com.pasteleriaerp.repository;
import com.pasteleriaerp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario,Long> { java.util.Optional<Usuario> findByUsername(String u); }
