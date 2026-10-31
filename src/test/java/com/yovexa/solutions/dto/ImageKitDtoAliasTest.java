package com.yovexa.solutions.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yovexa.solutions.dto.blog.BlogRequest;
import com.yovexa.solutions.dto.blog.BlogResponse;
import com.yovexa.solutions.dto.casestudy.CaseStudyRequest;
import com.yovexa.solutions.dto.casestudy.CaseStudyResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageKitDtoAliasTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testBlogRequest_JsonAliases() throws Exception {
        String jsonWithImage = "{\"title\":\"Test\",\"excerpt\":\"Excerpt\",\"content\":\"Content\",\"category\":\"ENG\",\"author\":\"Me\",\"image\":\"https://ik.imagekit.io/yovexa/img.png\"}";
        BlogRequest req1 = objectMapper.readValue(jsonWithImage, BlogRequest.class);
        assertEquals("https://ik.imagekit.io/yovexa/img.png", req1.getFeaturedImage());

        String jsonWithImageUrl = "{\"title\":\"Test\",\"excerpt\":\"Excerpt\",\"content\":\"Content\",\"category\":\"ENG\",\"author\":\"Me\",\"imageUrl\":\"https://ik.imagekit.io/yovexa/img2.png\"}";
        BlogRequest req2 = objectMapper.readValue(jsonWithImageUrl, BlogRequest.class);
        assertEquals("https://ik.imagekit.io/yovexa/img2.png", req2.getFeaturedImage());

        String jsonWithThumbnailUrl = "{\"title\":\"Test\",\"excerpt\":\"Excerpt\",\"content\":\"Content\",\"category\":\"ENG\",\"author\":\"Me\",\"thumbnailUrl\":\"https://ik.imagekit.io/yovexa/img3.png\"}";
        BlogRequest req3 = objectMapper.readValue(jsonWithThumbnailUrl, BlogRequest.class);
        assertEquals("https://ik.imagekit.io/yovexa/img3.png", req3.getFeaturedImage());

        String jsonWithFeaturedImage = "{\"title\":\"Test\",\"excerpt\":\"Excerpt\",\"content\":\"Content\",\"category\":\"ENG\",\"author\":\"Me\",\"featuredImage\":\"https://ik.imagekit.io/yovexa/img4.png\"}";
        BlogRequest req4 = objectMapper.readValue(jsonWithFeaturedImage, BlogRequest.class);
        assertEquals("https://ik.imagekit.io/yovexa/img4.png", req4.getFeaturedImage());
    }

    @Test
    void testCaseStudyRequest_JsonAliases() throws Exception {
        String jsonWithImage = "{\"title\":\"CS\",\"summary\":\"Summary\",\"problem\":\"Prob\",\"solution\":\"Sol\",\"image\":\"https://ik.imagekit.io/yovexa/cs.png\"}";
        CaseStudyRequest req1 = objectMapper.readValue(jsonWithImage, CaseStudyRequest.class);
        assertEquals("https://ik.imagekit.io/yovexa/cs.png", req1.getFeaturedImage());

        String jsonWithImageUrl = "{\"title\":\"CS\",\"summary\":\"Summary\",\"problem\":\"Prob\",\"solution\":\"Sol\",\"imageUrl\":\"https://ik.imagekit.io/yovexa/cs2.png\"}";
        CaseStudyRequest req2 = objectMapper.readValue(jsonWithImageUrl, CaseStudyRequest.class);
        assertEquals("https://ik.imagekit.io/yovexa/cs2.png", req2.getFeaturedImage());

        String jsonWithThumbnailUrl = "{\"title\":\"CS\",\"summary\":\"Summary\",\"problem\":\"Prob\",\"solution\":\"Sol\",\"thumbnailUrl\":\"https://ik.imagekit.io/yovexa/cs3.png\"}";
        CaseStudyRequest req3 = objectMapper.readValue(jsonWithThumbnailUrl, CaseStudyRequest.class);
        assertEquals("https://ik.imagekit.io/yovexa/cs3.png", req3.getFeaturedImage());

        String jsonWithFeaturedImage = "{\"title\":\"CS\",\"summary\":\"Summary\",\"problem\":\"Prob\",\"solution\":\"Sol\",\"featuredImage\":\"https://ik.imagekit.io/yovexa/cs4.png\"}";
        CaseStudyRequest req4 = objectMapper.readValue(jsonWithFeaturedImage, CaseStudyRequest.class);
        assertEquals("https://ik.imagekit.io/yovexa/cs4.png", req4.getFeaturedImage());
    }

    @Test
    void testResponses_IncludeBothFeaturedImageAndImageAliases() throws Exception {
        BlogResponse blogResponse = BlogResponse.builder()
                .id("1")
                .featuredImage("https://ik.imagekit.io/yovexa/blog.jpg")
                .image("https://ik.imagekit.io/yovexa/blog.jpg")
                .thumbnailUrl("https://ik.imagekit.io/yovexa/blog.jpg")
                .build();

        String blogJson = objectMapper.writeValueAsString(blogResponse);
        assertTrue(blogJson.contains("\"featuredImage\":\"https://ik.imagekit.io/yovexa/blog.jpg\""));
        assertTrue(blogJson.contains("\"image\":\"https://ik.imagekit.io/yovexa/blog.jpg\""));
        assertTrue(blogJson.contains("\"thumbnailUrl\":\"https://ik.imagekit.io/yovexa/blog.jpg\""));

        CaseStudyResponse csResponse = CaseStudyResponse.builder()
                .id("2")
                .featuredImage("https://ik.imagekit.io/yovexa/cs.jpg")
                .image("https://ik.imagekit.io/yovexa/cs.jpg")
                .thumbnailUrl("https://ik.imagekit.io/yovexa/cs.jpg")
                .build();

        String csJson = objectMapper.writeValueAsString(csResponse);
        assertTrue(csJson.contains("\"featuredImage\":\"https://ik.imagekit.io/yovexa/cs.jpg\""));
        assertTrue(csJson.contains("\"image\":\"https://ik.imagekit.io/yovexa/cs.jpg\""));
        assertTrue(csJson.contains("\"thumbnailUrl\":\"https://ik.imagekit.io/yovexa/cs.jpg\""));
    }
}
