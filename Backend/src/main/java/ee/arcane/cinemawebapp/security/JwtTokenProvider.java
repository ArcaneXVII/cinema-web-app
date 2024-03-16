package ee.arcane.cinemawebapp.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;

public class JwtTokenProvider {

    private static final byte[] SECRET = "CWAuYDsGhvufKCIxcKFL2jLZvbp4PihU".getBytes();
    static final Key jwtKey = Keys.hmacShaKeyFor(SECRET);
    // Expiration time set to 7 days (1000L * 60 * 60 * 24 * 7)
    private static final long EXPIRATION = 1000L * 60 * 60 * 24 * 7;

    private JwtTokenProvider() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateToken(String userId) {
        return Jwts.builder()
                .setSubject("cinemaAuth")
                .claim("userId", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(jwtKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", String.class);
    }
}
