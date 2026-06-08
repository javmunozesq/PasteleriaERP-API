package com.pasteleriaerp.model;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="categorias") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Categoria {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private String nombre, descripcion;
}
