package kmve.afw.managerpassword.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import kmve.afw.managerpassword.model.User;
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


    public JwtProvider(@Value("${jwt.secret.access}") String jwtAccessToken) {
        this.jwtAccessToken = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessToken));
    }

    public String generateAccessToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant expiredAccessInstant = now.plusMinutes(12).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpired = Date.from(expiredAccessInstant);

        return Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(accessExpired)
                .signWith(jwtAccessToken)
                .claim("roles", user.getRoles())
                .compact();
    }

    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token, jwtAccessToken);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
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

    public Claims getAccessToken(@NonNull String token) {
        return getClaims(token, jwtAccessToken);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
