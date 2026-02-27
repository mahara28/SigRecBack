package com.mc.back.sigrecette.security;


import com.mc.back.sigrecette.model.AdmUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtSecurity {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    @Autowired
    private JwtUtil jwtUtil;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generate(AdmUser admUser, String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", admUser.getId());
        return doGenerateToken(claims, admUser.getUsername(), type);
    }

    private String doGenerateToken(Map<String, Object> claims, String username, String type) {
        long expirationTimeLong;
        if ("ACCESS".equals(type)) {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000;
        } else {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000 * 5;
        }
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);

        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(createdDate)
                .setExpiration(expirationDate).signWith(key).compact();
    }

    public Long getIdFromToken(String token) {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        Claims cc = jwtUtil.getAllClaimsFromToken(token);
        return Long.valueOf(cc.get("id").toString());
    }

    public Object getUserInfoFromToken(String token) {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        Claims cc = jwtUtil.getAllClaimsFromToken(token);
        return cc.get("userInfo");
    }

}
