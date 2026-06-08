package com.pasteleriaerp.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*; import lombok.*;
import java.time.LocalDate; import java.util.*;
@Entity @Table(name="ventas") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Venta {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name="cliente_id") private Cliente cliente;
    private LocalDate fecha;
    @Enumerated(EnumType.STRING) @Builder.Default private Estado estado = Estado.COMPLETADA;
    @Builder.Default private double total = 0.0;
    private String observaciones;
    @OneToMany(mappedBy="venta",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
    @JsonManagedReference @Builder.Default private List<VentaLinea> lineas = new ArrayList<>();
    public enum Estado { COMPLETADA, CANCELADA }
}
