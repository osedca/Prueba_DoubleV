package com.doublev.prueba.hotel.repository;

import com.doublev.prueba.hotel.model.Ticket;
import com.doublev.prueba.hotel.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository // Agregar anotación para mejor organización en Spring Boot
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findByStatus(Ticket.StatusTicket status);

    List<Ticket> findByUsuario(Usuario usuario);
}