package com.yovexa.solutions.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageUrlUtilsTest {

    @Test
    void testResolveImageUrl_NullAndBlank() {
        assertNull(ImageUrlUtils.resolveImageUrl(null));
        assertNull(ImageUrlUtils.resolveImageUrl(""));
        assertNull(ImageUrlUtils.resolveImageUrl("   "));
    }

    @Test
    void testResolveImageUrl_ValidUrls() {
        assertEquals("https://ik.imagekit.io/yovexa/image.jpg",
                ImageUrlUtils.resolveImageUrl("https://ik.imagekit.io/yovexa/image.jpg"));

        assertEquals("https://ik.imagekit.io/yovexa/image.jpg",
                ImageUrlUtils.resolveImageUrl("  https://ik.imagekit.io/yovexa/image.jpg  "));
    }

    @Test
    void testResolveImageUrl_QuotedUrls() {
        assertEquals("https://ik.imagekit.io/yovexa/image.jpg",
                ImageUrlUtils.resolveImageUrl("\"https://ik.imagekit.io/yovexa/image.jpg\""));

        assertEquals("https://ik.imagekit.io/yovexa/image.jpg",
                ImageUrlUtils.resolveImageUrl("'https://ik.imagekit.io/yovexa/image.jpg'"));
    }

    @Test
    void testResolveImageUrl_JsonPayload() {
        String json = "{\"fileId\":\"123\",\"url\":\"https://ik.imagekit.io/yovexa/photo.png\"}";
        assertEquals("https://ik.imagekit.io/yovexa/photo.png",
                ImageUrlUtils.resolveImageUrl(json));
    }

    @Test
    void testResolveImageUrl_JsonPayloadWithEscapedSlashes() {
        String json = "{\"fileId\":\"123\",\"url\":\"https:\\/\\/ik.imagekit.io\\/yovexa\\/photo.png\"}";
        assertEquals("https://ik.imagekit.io/yovexa/photo.png",
                ImageUrlUtils.resolveImageUrl(json));
    }
}
