package com.grocery.shop.security;

import com.grocery.shop.model.Role;
import com.grocery.shop.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import static com.grocery.shop.security.JwtConstants.ACCESS_TOKEN_VALIDITY_SECONDS;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1628975668224637884L;

    private final String SIGNING_KEY = "823dcb227a07d6d4b6b4f573a7bb75827597a17880b4e383b8f8b48a8dc0bab9";

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public List<SimpleGrantedAuthority> getRoleFromToken(String token) {
        ArrayList<LinkedHashMap<String, String>> scopes =
                getClaimFromToken(token, claims -> claims.get("scopes", ArrayList.class));

        return scopes.stream()
                .flatMap(scope -> scope.values().stream())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getEmail(), user.getRole());
    }

    private String doGenerateToken(String subject, Role role) {

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", List.of(new SimpleGrantedAuthority(role.toString())));

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }


}
