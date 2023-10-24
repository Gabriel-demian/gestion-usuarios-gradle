package com.bci.gestionusuarios.service;

import com.bci.gestionusuarios.entity.UserEntity;
import com.bci.gestionusuarios.service.impl.TokenServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class TokenServiceImplTest {

    @Autowired
    private TokenServiceImpl tokenServiceImpl;

    public static final UUID uuid = UUID.randomUUID();

    @Test
    public void testToToken() {
        UserEntity user = new UserEntity();
        user.setId(uuid);

        String token = tokenServiceImpl.toToken(user);

        Map<String, Object> claims = tokenServiceImpl.getClaims(token);

        assertEquals(user.getId(), UUID.fromString(claims.get("UserId").toString()));
    }

    @Test
    public void testGetClaims() {
        UserEntity user = new UserEntity();
        user.setId(uuid);
        String token = tokenServiceImpl.toToken(user);

        Map<String, Object> claims = tokenServiceImpl.getClaims(token);

        assertEquals(user.getId(), UUID.fromString(claims.get("UserId").toString()));
    }


}