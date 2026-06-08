package com.pasteleriaerp.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*; import lombok.*;
import java.time.LocalDate; import java.util.*;
@Entity @Table(name="compras") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Compra {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name="proveedor_id") private Proveedor proveedor;
    private LocalDate fecha;
    @Enumerated(EnumType.STRING) @Builder.Default private Estado estado = Estado.PENDIENTE;
    @Builder.Default private double total = 0.0;
    private String observaciones;
    @OneToMany(mappedBy="compra",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
    @JsonManagedReference @Builder.Default private List<CompraLinea> lineas = new ArrayList<>();
    public enum Estado { PENDIENTE, RECIBIDA, CANCELADA }
}
