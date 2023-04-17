package com.kawe.belajarrest.services.Authentication;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // * secret key 
    private final static String secretKey = "597133743677397A244226452948404D635166546A576E5A7234753778214125";

    //* ekstrak username dari token yang dikirimkan
    public String ExtractUsername(String token){
        return ExtractClaim(token, Claims::getSubject);
    }
    
    //* parse semua claim jwt
    private Claims ExtractAllClaims(String token) {
        return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
    
    //* parse secretkey(string) menjadi key
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); 
        return Keys.hmacShaKeyFor(keyBytes);
    } 

    //* Extract Claim berdasarkan token yang sudah id parse 
    private <T> T ExtractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = ExtractAllClaims(token); 
        return claimResolver.apply(claims);
    }

    //* Menggenerate token berdasarkan user details dan extra claims 
    public String generateToken(Map<String, Object> extraClaims, UserDetails UserDetails){
        return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(UserDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *24))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
    } 

    //* Menggenerate token berdasarkan user details 
    public String generateToken(UserDetails userDetails) { 
        return generateToken(new HashMap<>(), userDetails);
    }

    //* Generate refresh token 
    public String generateRefreshToken(
        UserDetails userDetails
    ){
        return generateToken(new HashMap<>(), userDetails);
    } 
    
    //* Validasi jwttoken 
    public boolean IsTokenValid(String token, UserDetails userDetails) { 
        String username = ExtractUsername(token);
        return (username.equals(userDetails.getUsername())) && !IsTokenExpired(token);
    }

    //* Extract data expire dari token 
    public Date ExtractExpiration(String token){
        return ExtractClaim(token, Claims::getExpiration);
    }

    //* Validasi apakah token expire
    public boolean IsTokenExpired(String token){
        return ExtractExpiration(token).before(new Date());
    }
}
