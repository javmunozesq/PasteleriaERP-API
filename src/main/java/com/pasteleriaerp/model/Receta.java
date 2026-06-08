package com.pasteleriaerp.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*; import lombok.*;
import java.util.*;
@Entity @Table(name="recetas") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Receta {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String nombre;
    private String descripcion;
    @ManyToOne @JoinColumn(name="producto_final_id") private Producto productoFinal;
    @OneToMany(mappedBy="receta",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
    @JsonManagedReference @Builder.Default private List<RecetaIngrediente> ingredientes = new ArrayList<>();
}
