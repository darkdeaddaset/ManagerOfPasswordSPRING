package com.savin.mop.jwt;

import com.savin.mop.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    private final SecretKey jwtAccessToken;
    private final SecretKey jwtRefreshToken;

    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessToken,
            @Value("${jwt.secret.refresh}") String jwtRefreshToken){
        this.jwtAccessToken = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessToken));
        this.jwtRefreshToken = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshToken));
    }

    public String generateAccessToken(@NonNull User user){
        final LocalDateTime now = LocalDateTime.now();
        final Instant expiredAccessInstant = now.plusMinutes(12).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpired = Date.from(expiredAccessInstant);

        return Jwts.builder()
                .setSubject(user.getName())
                .setExpiration(accessExpired)
                .signWith(jwtAccessToken)
                .claim("role", user.getRoles())
                .compact();
    }

    public String generateRefreshToken(@NonNull User user){
        final LocalDateTime now = LocalDateTime.now();
        final Instant expiredRefreshTokenInstant = now.plusDays(15).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshToken = Date.from(expiredRefreshTokenInstant);

        return Jwts.builder()
                .setSubject(user.getName())
                .setExpiration(refreshToken)
                .signWith(jwtRefreshToken)
                .compact();
    }

    public boolean validateAccess(@NonNull String token){
        return validateToken(token, jwtAccessToken);
    }

    public boolean validateRefresh(@NonNull String token){
        return validateToken(token, jwtRefreshToken);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("Токен протух");
        } catch (UnsupportedJwtException ex) {
            log.error("Неподдерживаемый токен");
        } catch (SignatureException ex) {
            log.error("Неподдерживаемая сигнатура");
        } catch (Exception ex) {
            log.error("Неподдерживаемый токен", ex);
        }
        return false;
    }

    public Claims getAccessToken(@NonNull String token){
        return getClaims(token, jwtAccessToken);
    }

    public Claims getRefreshToken(@NonNull String token){
        return getClaims(token, jwtRefreshToken);
    }

    private Claims getClaims(@NonNull String token, @NonNull SecretKey secret){
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
