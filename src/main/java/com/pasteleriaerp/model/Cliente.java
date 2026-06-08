package com.pasteleriaerp.model;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="clientes") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cliente {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String nombre;
    private String nif, email, telefono, direccion;
    @Builder.Default private boolean activo = true;
}
