package com.pasteleriaerp.repository;
import com.pasteleriaerp.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VentaRepository extends JpaRepository<Venta,Long> { java.util.List<Venta> findTop10ByOrderByFechaDesc(); }
