package com.bci.gestionusuarios.service.impl;

import com.bci.gestionusuarios.entity.UserEntity;

import io.jsonwebtoken.*;

import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class TokenServiceImpl {

    public static final String userId = "UserId";
    private static final String claveFirmada = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=234236sdfgb474FSdssdlknfslan";
    private static final Key secretKey = new SecretKeySpec(Base64.getEncoder().encode(claveFirmada.getBytes()), SignatureAlgorithm.HS256.getJcaName());

    public String toToken(UserEntity user){
        return Jwts.builder()
                .setIssuer("gestion-usuarios")
                .claim(userId, user.getId())
                .setIssuedAt(new Date())
                // vence dentro de una hora
                .setExpiration(new Date(new Date().getTime()+3600000))
                .signWith(
                        secretKey
                )
                .compact();
    }

    public Map<String, Object> getClaims(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parse(token, mapJWTHandler);
    }

    private static final JwtHandler<Map<String, Object>> mapJWTHandler = new JwtHandlerAdapter<Map<String, Object>>() {
        @Override
        public Map<String, Object> onClaimsJws(Jws<Claims> jws) {
            return jws.getBody();
        }
    };

}
