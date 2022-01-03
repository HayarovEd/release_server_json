package service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtService {
    private final String secretKey = "geiwodiangasfdjsikolkjikolkijswe";
    public String generateToken(String user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        final String jwtToken = Jwts.builder()
                .setSubject(user)
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return jwtToken;
    }
}
