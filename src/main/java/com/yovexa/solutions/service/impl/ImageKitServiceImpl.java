package com.yovexa.solutions.service.impl;

import com.yovexa.solutions.dto.imagekit.ImageKitAuthResponse;
import com.yovexa.solutions.service.ImageKitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.UUID;

@Slf4j
@Service
public class ImageKitServiceImpl implements ImageKitService {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    @Value("${imagekit.public-key:${imagekit.publicKey:${IMAGEKIT_PUBLIC_KEY:${Imagekit_Public:${imagekit_public:}}}}}")
    private String publicKey;

    @Value("${imagekit.private-key:${imagekit.privateKey:${IMAGEKIT_PRIVATE_KEY:${Imagekit_Private:${imagekit_private:}}}}}")
    private String privateKey;

    @Value("${imagekit.expire-seconds:${imagekit.expireSeconds:${IMAGEKIT_EXPIRE_SECONDS:1800}}}")
    private long expireSeconds;

    @Override
    public ImageKitAuthResponse getAuthenticationParameters() {
        if (privateKey == null || privateKey.isBlank()) {
            throw new IllegalStateException("ImageKit private key is not configured in application properties or environment variables.");
        }
        String resolvedPublicKey = (publicKey != null) ? publicKey.trim() : "";
        if (resolvedPublicKey.isBlank()) {
            log.warn("ImageKit public key is not configured in application properties or environment variables.");
        }

        String token = UUID.randomUUID().toString();
        long expire = (System.currentTimeMillis() / 1000) + (expireSeconds > 0 ? expireSeconds : 1800);
        String signature = generateHmacSha1Signature(token + expire, privateKey.trim());

        return ImageKitAuthResponse.builder()
                .token(token)
                .expire(expire)
                .signature(signature)
                .publicKey(resolvedPublicKey)
                .success(true)
                .build();
    }

    private String generateHmacSha1Signature(String data, String key) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_SHA1_ALGORITHM);
            mac.init(secretKeySpec);
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            log.error("Failed to generate ImageKit HMAC-SHA1 signature: {}", ex.getMessage());
            throw new RuntimeException("Could not generate ImageKit authentication signature: " + ex.getMessage(), ex);
        }
    }
}
