package com.pasteleriaerp.model;
import jakarta.persistence.*; import lombok.*;
import java.time.LocalDate;
@Entity @Table(name="ordenes_produccion") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrdenProduccion {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name="receta_id") private Receta receta;
    private int cantidad; private LocalDate fecha;
    @Enumerated(EnumType.STRING) @Builder.Default private Estado estado = Estado.COMPLETADA;
    private String observaciones;
    public enum Estado { COMPLETADA, CANCELADA }
}
