package com.pasteleriaerp.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="venta_lineas") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VentaLinea {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name="venta_id") @JsonBackReference private Venta venta;
    @ManyToOne @JoinColumn(name="producto_id") private Producto producto;
    private int cantidad; private double precioUnitario;
    @Builder.Default private double iva = 10.0;
    public double getSubtotal() { return cantidad * precioUnitario; }
    public double getTotal()    { return getSubtotal() * (1 + iva/100); }
}
