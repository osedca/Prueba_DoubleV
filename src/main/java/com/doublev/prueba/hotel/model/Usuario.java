package com.doublev.prueba.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50)
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50)
    private String apellidos;

    private String password;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public String getPassword() { return password; }

    public Collection<? extends GrantedAuthority> getRoles() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }
}