package it.micael.vanini.cinemille.configuration.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import it.micael.vanini.cinemille.dto.UserDetailsImpl;
import it.micael.vanini.cinemille.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static it.micael.vanini.cinemille.dto.Error.JWT_NOT_VALID;

@Component
public class Jwtutils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Jwtutils.class);

    private final String jwtSecret;

    public Jwtutils(@Value("${it.micael.jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String generateJwtToken(Authentication authentication) {
        LOGGER.info("Generating JWT token");
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecret)), Jwts.SIG.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecret)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            LOGGER.error("Given JWT token non valid");
            //TODO: lanciare la giusta eccezione
            return null;
        }
    }

    public void validateJwtToken(String token) throws AuthenticationException {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecret)))
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            LOGGER.info("Given JWT token non valid");
            throw new AuthenticationException(JWT_NOT_VALID);
        }
    }
}
