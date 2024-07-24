package me.desafio.blog.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import me.desafio.blog.infra.exception.InvalidJwtTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String generateToken(String email, String userId) {
        try {
            return JWT.create()
                    .withSubject(email)
                    .withClaim("userId", userId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                    .sign(com.auth0.jwt.algorithms.Algorithm.HMAC512(secret.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public String extractUserId(String token) {
        try {
            JWTVerifier verifier = JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC512(secret.getBytes())).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            throw new InvalidJwtTokenException("Invalid JWT token", e);
        } catch (JWTVerificationException e) {
            throw new InvalidJwtTokenException("Error verifying JWT token", e);
        }
    }

    public String extractEmail(String token) {
        try {
            JWTVerifier verifier = JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC512(secret.getBytes())).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTDecodeException e) {
            throw new InvalidJwtTokenException("Invalid JWT token", e);
        } catch (JWTVerificationException e) {
            throw new InvalidJwtTokenException("Error verifying JWT token", e);
        }
    }

    public boolean validateToken(String token, String email) {
        try {
            String emailFromToken = extractEmail(token);
            return emailFromToken.equals(email) && !isTokenExpired(token);
        }
        catch (InvalidJwtTokenException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            JWTVerifier verifier = JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC512(secret.getBytes())).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Date expirationDate = decodedJWT.getExpiresAt();
            return expirationDate.before(new Date());
        } catch (JWTVerificationException e) {
            throw new InvalidJwtTokenException("Error checking if JWT token is expired", e);
        }
    }
}
