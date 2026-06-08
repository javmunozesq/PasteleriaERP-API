package com.pasteleriaerp.repository;
import com.pasteleriaerp.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CompraRepository extends JpaRepository<Compra,Long> { java.util.List<Compra> findTop10ByOrderByFechaDesc(); }
