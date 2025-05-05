package com.doublev.prueba.hotel.security;

import com.doublev.prueba.hotel.model.Usuario;
import com.doublev.prueba.hotel.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByNombresIgnoreCase(username);

        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        return User.builder()
                .username(usuario.get().getNombres())
                .password(usuario.get().getPassword()) // Ahora usa la contraseña real encriptada
                .authorities(usuario.get().getRoles() != null ? usuario.get().getRoles() : Collections.emptyList()) // 🔹 Manejar roles dinámicamente
                .build();
    }
}