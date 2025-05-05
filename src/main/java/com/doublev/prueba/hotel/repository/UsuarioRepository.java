package com.doublev.prueba.hotel.repository;

import com.doublev.prueba.hotel.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository // Agregar anotación para mejorar organización en Spring Boot
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByNombres(String nombres);

    Optional<Usuario> findByNombresIgnoreCase(String nombres); // Permitir búsquedas sin importar mayúsculas/minúsculas

    Optional<Usuario> findByNombresAndApellidos(String nombres, String apellidos); // Buscar por nombre y apellido juntos
}