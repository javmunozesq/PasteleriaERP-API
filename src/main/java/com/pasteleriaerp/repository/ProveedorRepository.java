package com.pasteleriaerp.repository;
import com.pasteleriaerp.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProveedorRepository extends JpaRepository<Proveedor,Long> { java.util.List<Proveedor> findByActivoTrue(); }
