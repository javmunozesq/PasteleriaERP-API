package com.pasteleriaerp.model;
import jakarta.persistence.*; import lombok.*;
import java.time.LocalDate;
@Entity @Table(name="movimientos_stock") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MovimientoStock {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name="producto_id") private Producto producto;
    @Enumerated(EnumType.STRING) private Tipo tipo;
    private double cantidad; private LocalDate fecha; private String referencia;
    public enum Tipo { ENTRADA_COMPRA, SALIDA_VENTA, CONSUMO_PRODUCCION, PRODUCCION, AJUSTE }
}
