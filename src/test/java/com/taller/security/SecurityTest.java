package com.taller.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityTest {

    private static final String PASSWORD = "12345";
    private static final String PLAIN_TEXT = "the quick brown fox";
    private static final byte[] PLAIN_BYTES = PLAIN_TEXT.getBytes(StandardCharsets.UTF_8);

    @Autowired
    private Security security;
    

    @Test
    public void encrypt() throws Exception {
        final byte[] encryptedBytes = security.encriptar(PLAIN_BYTES, PASSWORD);
        assertNotEquals(PLAIN_BYTES, encryptedBytes);
    }

    @Test
    public void decrypt() throws Exception {
        final byte[] encryptedBytes = security.encriptar(PLAIN_BYTES, PASSWORD);
        final byte[] decryptedBytes = security.desencriptar(encryptedBytes, PASSWORD);
        assertEquals(PLAIN_TEXT, new String(decryptedBytes));
    }


}
