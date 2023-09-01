package com.libertadores.pensiones.utils;

import com.libertadores.pensiones.model.UsuarioResponse;
import com.libertadores.pensiones.persistence.entity.UserEntity;
import com.libertadores.pensiones.persistence.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private Long expirationMs;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(expiration)
                //.signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractExpirationDate(token);
        return expirationDate.before(new Date());
    }

    public String validationOfUserFields(String username) throws Exception {

        UserEntity entity = userRepository.findByUsername(username);
        if (entity.getEstado().equals("inactivo")){
            throw new Exception("Usuario inactivo");
        } else if (entity.getTipoUsuario().equals("null")) {
            throw new Exception("Usuario no encuentra con un rol definido");
        }
        return entity.getTipoUsuario();
    }

    public String obtainUser(String username) throws Exception {
        UserEntity entity = userRepository.findByUsername(username);
        return entity.getUsername();
    }


    public Integer obtainIdUser(String username) throws Exception {
        UserEntity entity = userRepository.findByUsername(username);
        return entity.getIdUsuario();
    }



}


