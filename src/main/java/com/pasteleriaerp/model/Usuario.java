package com.pasteleriaerp.model;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="usuarios") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(unique=true,nullable=false) private String username;
    @Column(nullable=false) private String password;
    private String nombreCompleto;
    @Enumerated(EnumType.STRING) @Builder.Default private Rol rol = Rol.OPERARIO;
    @Builder.Default private boolean activo = true;
    public enum Rol { ADMIN, OPERARIO }
}
