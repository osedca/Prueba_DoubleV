package com.doublev.prueba.hotel.controller;

import com.doublev.prueba.hotel.model.Usuario;
import com.doublev.prueba.hotel.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*") //Permitir acceso desde el navegador y clientes externos
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        if (usuario.getNombres() == null || usuario.getApellidos() == null) {
            return ResponseEntity.badRequest().body(null); //Evitar creación de usuarios incompletos
        }
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable UUID id, @RequestBody Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    if (usuarioActualizado.getNombres() != null) {
                        usuario.setNombres(usuarioActualizado.getNombres());
                    }
                    if (usuarioActualizado.getApellidos() != null) {
                        usuario.setApellidos(usuarioActualizado.getApellidos());
                    }
                    Usuario usuarioGuardado = usuarioRepository.save(usuario);
                    return ResponseEntity.ok(usuarioGuardado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}