package com.comunidad.comunidad_backend.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.comunidad.comunidad_backend.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    public String generarToken(Usuario usuario){
        Map<String, Object> datosToken = new HashMap<>();
        datosToken.put("idUsuario", usuario.getId());
        datosToken.put("idComunidad", usuario.getComunidad().getId());
        datosToken.put("rol", usuario.getRol());

        return Jwts.builder()
                    .setClaims(datosToken)
                    .setSubject(usuario.getEmail())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .signWith(obtenerFirma(), SignatureAlgorithm.HS256)
                    .compact();
    }

    private SecretKey obtenerFirma(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extraerUsername(String token){
        Claims claims = extraerClaims(token);
        return claims.getSubject();

    }

    private Claims extraerClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(obtenerFirma())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean esTokenValido(String token, UserDetails userDetails){
        final String username = extraerUsername(token);
        return (username.equals(userDetails.getUsername())) && !estaTokenCaducado(token); 
    }

    private boolean estaTokenCaducado(String token){
        Date fechaCaducidad = extraerClaims(token).getExpiration();
        return fechaCaducidad.before(new Date());
    }
    


    
}
