package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.imagekit.ImageKitAuthResponse;

public interface ImageKitService {

    /**
     * Generates authentication parameters for client-side uploads to ImageKit.
     * Includes unique upload token, expiration timestamp, HMAC-SHA1 signature, and public key.
     *
     * @return {@link ImageKitAuthResponse} containing token, expire, signature, and publicKey
     */
    ImageKitAuthResponse getAuthenticationParameters();
}
