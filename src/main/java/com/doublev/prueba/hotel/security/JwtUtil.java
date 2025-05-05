package com.doublev.prueba.hotel.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY_STRING = "clave_super_secreta";
    private static final SecretKey SECRET_KEY = new SecretKeySpec(
        SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8),
        SignatureAlgorithm.HS256.getJcaName()
    );

    public String generarToken(String usuario) {
        return Jwts.builder()
                .setSubject(usuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validarToken(String token, String usuario) {
        String usuarioDelToken = extraerUsuario(token);
        return usuarioDelToken != null && usuario.equals(usuarioDelToken) && !estaExpirado(token);
    }

    public String extraerUsuario(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public Date extraerFechaExpiracion(String token) {
        return extraerClaim(token, Claims::getExpiration);
    }

    private <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            JwtParser jwtParser = Jwts.parser().setSigningKey(SECRET_KEY).build();
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean estaExpirado(String token) {
        Date fechaExpiracion = extraerFechaExpiracion(token);
        return fechaExpiracion != null && fechaExpiracion.before(new Date());
    }
}