package com.pasteleriaerp.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="compra_lineas") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompraLinea {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name="compra_id") @JsonBackReference private Compra compra;
    @ManyToOne @JoinColumn(name="producto_id") private Producto producto;
    private int cantidad; private double precioUnitario;
    public double getSubtotal() { return cantidad * precioUnitario; }
}
