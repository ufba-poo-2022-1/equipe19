package com.api.ticketshop.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

        private static final long serialVersionUID = -2550185165626007488L;

        public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

        @Value("${jwt.secret}")
        private String secret;


        private Claims getAllClaimsFromToken(String token) {
                return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }

        public <T>T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
                final Claims claims = getAllClaimsFromToken(token);
                return claimsResolver.apply(claims);
        }

        public String getIDFromToken(String token){
                Claims claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token).getBody();

               return claims.get("userID").toString();
        }

        public String getTypeFromToken(String token){
                Claims claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token).getBody();

                return claims.get("type").toString();
        }

        private String doGenerateToken(Map<String, Object> claims, String subject, int userID, int userType){
                return Jwts.builder().setClaims(claims)
                        .setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                        .claim("userID", userID)
                        .claim("type", userType)
                        .signWith(SignatureAlgorithm.HS512, secret).compact();
        }

        public Boolean validateToken(String token, UserDetails userDetails) {
                final String username = getUsernameFromToken(token);
                return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

        public String generateToken(UserDetails userDetails, int userID, int type) {
                Map<String, Object> claims = new HashMap<>();
                return doGenerateToken(claims, userDetails.getUsername(), userID, type);
        }

        // get expiration date from jwt token
        public Date getExpirationDateFromToken(String token) {
                return getClaimFromToken(token, Claims::getExpiration);
        }

        // get username from token
        public String getUsernameFromToken(String token) {
                return getClaimFromToken(token, Claims::getSubject);
        }

        private Boolean isTokenExpired(String token) {
                final Date expiration = getExpirationDateFromToken(token);
                return expiration.before(new Date());
        }

}
