package com.pasteleriaerp.model;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="productos") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Producto {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String nombre;
    private String descripcion;
    @ManyToOne @JoinColumn(name="categoria_id") private Categoria categoria;
    private String unidadMedida;
    @Builder.Default private double precioCompra=0.0, precioVenta=0.0, stock=0.0, stockMinimo=0.0;
    @Enumerated(EnumType.STRING) @Builder.Default private Tipo tipo = Tipo.MATERIA_PRIMA;
    @Builder.Default private boolean activo = true;
    public enum Tipo { MATERIA_PRIMA, PRODUCTO_TERMINADO }
    public boolean isStockBajo() { return stock <= stockMinimo; }
}
