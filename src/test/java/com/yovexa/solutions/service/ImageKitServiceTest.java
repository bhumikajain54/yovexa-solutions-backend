package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.imagekit.ImageKitAuthResponse;
import com.yovexa.solutions.service.impl.ImageKitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;

public class ImageKitServiceTest {

    private ImageKitServiceImpl imageKitService;

    @BeforeEach
    public void setUp() {
        imageKitService = new ImageKitServiceImpl();
        ReflectionTestUtils.setField(imageKitService, "publicKey", "public_test_key_123");
        ReflectionTestUtils.setField(imageKitService, "privateKey", "private_test_key_456");
        ReflectionTestUtils.setField(imageKitService, "expireSeconds", 1800L);
    }

    @Test
    void testGetAuthenticationParameters_Success() throws Exception {
        long beforeEpoch = System.currentTimeMillis() / 1000;
        ImageKitAuthResponse response = imageKitService.getAuthenticationParameters();
        long afterEpoch = System.currentTimeMillis() / 1000;

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertFalse(response.getToken().isBlank());

        assertEquals("public_test_key_123", response.getPublicKey());
        assertTrue(response.isSuccess());

        // Check expiration is around 1800 seconds in the future
        assertTrue(response.getExpire() >= beforeEpoch + 1800);
        assertTrue(response.getExpire() <= afterEpoch + 1800);

        // Verify HMAC-SHA1 signature matches expected computation
        String dataToSign = response.getToken() + response.getExpire();
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secretKeySpec = new SecretKeySpec("private_test_key_456".getBytes(StandardCharsets.UTF_8), "HmacSHA1");
        mac.init(secretKeySpec);
        String expectedSignature = HexFormat.of().formatHex(mac.doFinal(dataToSign.getBytes(StandardCharsets.UTF_8)));

        assertEquals(expectedSignature, response.getSignature());
        assertEquals(40, response.getSignature().length()); // SHA1 produces 40 hex chars

        // Verify compatibility data map
        assertNotNull(response.getData());
        assertEquals(response.getToken(), response.getData().get("token"));
        assertEquals(response.getExpire(), response.getData().get("expire"));
        assertEquals(response.getSignature(), response.getData().get("signature"));
        assertEquals(response.getPublicKey(), response.getData().get("publicKey"));
    }

    @Test
    void testMissingPrivateKey_ThrowsIllegalStateException() {
        ReflectionTestUtils.setField(imageKitService, "privateKey", "");

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                imageKitService.getAuthenticationParameters()
        );
        assertTrue(ex.getMessage().contains("private key is not configured"));
    }

    @Test
    void testMissingPublicKey_ReturnsEmptyPublicKeyGracefully() {
        ReflectionTestUtils.setField(imageKitService, "publicKey", null);

        ImageKitAuthResponse response = imageKitService.getAuthenticationParameters();
        assertNotNull(response);
        assertEquals("", response.getPublicKey());
        assertNotNull(response.getSignature());
    }
}
