package com.doublev.prueba.hotel.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
            throws ServletException, IOException {

        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                chain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);
            Optional<String> usernameOpt = Optional.ofNullable(jwtUtil.extraerUsuario(token));

            if (usernameOpt.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails;
                try {
                    userDetails = userDetailsService.loadUserByUsername(usernameOpt.get());
                } catch (Exception e) {
                    log.error("Error al cargar usuario desde UserDetailsService: {}", e.getMessage());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no autorizado");
                    return;
                }

                if (jwtUtil.validarToken(token, usernameOpt.get())) {
                    UsernamePasswordAuthenticationToken authenticationToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error general en JwtFilter: {}", e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error en autenticación JWT");
        }
    }
}