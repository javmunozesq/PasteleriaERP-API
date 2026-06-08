package com.pasteleriaerp.repository;
import com.pasteleriaerp.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClienteRepository extends JpaRepository<Cliente,Long> { java.util.List<Cliente> findByActivoTrue(); }
