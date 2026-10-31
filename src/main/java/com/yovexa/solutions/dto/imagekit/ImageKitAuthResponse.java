package com.yovexa.solutions.dto.imagekit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ImageKit Client-Side Upload Authentication Parameters")
public class ImageKitAuthResponse {

    @Schema(description = "Unique upload token for ImageKit client upload", example = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
    private String token;

    @Schema(description = "Epoch timestamp in seconds when the upload token expires", example = "1730412345")
    private long expire;

    @Schema(description = "HMAC-SHA1 hex signature generated with private key", example = "43c0812e3e56a782b3d...")
    private String signature;

    @Schema(description = "ImageKit account public key", example = "public_your_key_here")
    private String publicKey;

    @Builder.Default
    @Schema(description = "Success status flag", example = "true")
    private boolean success = true;

    @JsonProperty(value = "data", access = JsonProperty.Access.READ_ONLY)
    @Schema(hidden = true)
    public Map<String, Object> getData() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("token", token != null ? token : "");
        map.put("expire", expire);
        map.put("signature", signature != null ? signature : "");
        map.put("publicKey", publicKey != null ? publicKey : "");
        return map;
    }
}
