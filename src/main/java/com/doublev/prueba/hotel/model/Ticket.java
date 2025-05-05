package com.doublev.prueba.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // Agregar constructor para facilitar creación de instancias
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false) // Asegurar que la descripción no sea nula en la BD
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private StatusTicket status;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore // Evitar problemas de referencia cíclica si se presentan
    private Usuario usuario;

    public enum StatusTicket {
        PENDIENTE,
        EN_PROCESO,
        FINALIZADO
    }
}