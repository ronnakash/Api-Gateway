package com.gmt.gateway.service;

import com.gmt.gateway.response.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtTokenService implements Serializable {

    @Value("${jwt.expiration}")
    public long jwtTokenValidity;

    @Value("${jwt.secret}")
    private String secret;

    public String getIdentificationFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, String claim, Class<T> claimClass) {
        return getClaimFromToken(token, claims -> claims.get(claim, claimClass));
    }

    private  <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

//    private boolean ignoreTokenExpiration(String token) {
//        // here you specify tokens, for that the expiration is ignored
//        return false;
//    }

    public String generateToken(UserDetails userDetails, String authToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("auth-token", authToken);
        return doGenerateToken(claims, userDetails.id(), userDetails.identifier());
    }

    private String doGenerateToken(Map<String, Object> claims, long id, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setId(String.valueOf(id))
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity *1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

//    public boolean canTokenBeRefreshed(String token) {
//        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
//    }

    public boolean validateToken(String token, String authToken) {
        String authTokenFromJwt = getClaim(token, "auth-token", String.class);
        return (Objects.equals(authTokenFromJwt, authToken) && !isTokenExpired(token));
    }
}
