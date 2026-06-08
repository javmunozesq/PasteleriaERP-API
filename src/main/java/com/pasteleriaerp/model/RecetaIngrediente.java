package com.pasteleriaerp.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="receta_ingredientes") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecetaIngrediente {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name="receta_id") @JsonBackReference private Receta receta;
    @ManyToOne @JoinColumn(name="producto_id") private Producto ingrediente;
    private double cantidad;
}
