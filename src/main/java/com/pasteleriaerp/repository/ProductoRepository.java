package com.pasteleriaerp.repository;
import com.pasteleriaerp.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductoRepository extends JpaRepository<Producto,Long> { java.util.List<Producto> findByActivoTrue(); java.util.List<Producto> findByActivoTrueAndTipo(Producto.Tipo t); }
