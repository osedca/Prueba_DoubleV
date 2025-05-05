package com.doublev.prueba.hotel.controller;

import com.doublev.prueba.hotel.model.Ticket;
import com.doublev.prueba.hotel.model.Usuario;
import com.doublev.prueba.hotel.repository.TicketRepository;
import com.doublev.prueba.hotel.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*") 
public class TicketController {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    public TicketController(TicketRepository ticketRepository, UsuarioRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<Ticket> obtenerTickets() {
        return ticketRepository.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerTicketPorId(@PathVariable UUID id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Ticket> crearTicket(@RequestBody Ticket ticket) {
        if (ticket.getDescripcion() == null || ticket.getStatus() == null) {
            return ResponseEntity.badRequest().body(null); //Evitar tickets sin información
        }
        Ticket nuevoTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(nuevoTicket);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> editarTicket(@PathVariable UUID id, @RequestBody Ticket ticketActualizado) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    if (ticketActualizado.getDescripcion() != null) {
                        ticket.setDescripcion(ticketActualizado.getDescripcion());
                    }
                    if (ticketActualizado.getStatus() != null) {
                        ticket.setStatus(ticketActualizado.getStatus());
                    }
                    Ticket ticketGuardado = ticketRepository.save(ticket);
                    return ResponseEntity.ok(ticketGuardado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTicket(@PathVariable UUID id) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticketRepository.delete(ticket);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/filtrar")
    public ResponseEntity<List<Ticket>> filtrarPorEstado(@RequestParam Ticket.StatusTicket status) {
        List<Ticket> tickets = ticketRepository.findByStatus(status);
        return ResponseEntity.ok(tickets);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Ticket>> filtrarPorUsuario(@PathVariable UUID usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // Evitar `RuntimeException`, devolver `404`
        }
        List<Ticket> tickets = ticketRepository.findByUsuario(usuarioOpt.get());
        return ResponseEntity.ok(tickets);
    }
}