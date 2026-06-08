package com.pasteleriaerp.repository;
import com.pasteleriaerp.model.OrdenProduccion;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrdenProduccionRepository extends JpaRepository<OrdenProduccion,Long> { java.util.List<OrdenProduccion> findTop10ByOrderByFechaDesc(); }
