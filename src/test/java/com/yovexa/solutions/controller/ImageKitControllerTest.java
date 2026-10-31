package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.imagekit.ImageKitAuthResponse;
import com.yovexa.solutions.service.ImageKitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageKitControllerTest {

    @Mock
    private ImageKitService imageKitService;

    @InjectMocks
    private ImageKitController imageKitController;

    @Test
    void testGetAuthParameters_ReturnsOk() {
        ImageKitAuthResponse mockResponse = ImageKitAuthResponse.builder()
                .token("test-uuid-token")
                .expire(1730000000L)
                .signature("sample-hmac-sha1-signature")
                .publicKey("public_test_key")
                .success(true)
                .build();

        when(imageKitService.getAuthenticationParameters()).thenReturn(mockResponse);

        ResponseEntity<ImageKitAuthResponse> responseEntity = imageKitController.getAuthParameters();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        ImageKitAuthResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("test-uuid-token", body.getToken());
        assertEquals(1730000000L, body.getExpire());
        assertEquals("sample-hmac-sha1-signature", body.getSignature());
        assertEquals("public_test_key", body.getPublicKey());
        verify(imageKitService, times(1)).getAuthenticationParameters();
    }
}
