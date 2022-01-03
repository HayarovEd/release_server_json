package service;


import io.jsonwebtoken.*;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtService {
    private final String secretKey = "geiwodiangasfdjsikolkjikolkijswe";
    public String generateToken(String user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusYears(1).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user)
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token);
    }
    private boolean validateToken(@NonNull String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            System.out.println("Token expired" + expEx);
        } catch (UnsupportedJwtException unsEx) {
            System.out.println("Unsupported jwt" + unsEx);
        } catch (MalformedJwtException mjEx) {
            System.out.println("Malformed jwt" + mjEx);
        } catch (SignatureException sEx) {
            System.out.println("Invalid signature" + sEx);
        } catch (Exception e) {
            System.out.println("invalid token" + e);
        }
        return false;
    }
    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, secretKey);
    }

    private Claims getClaims(@NonNull String token, @NonNull String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
