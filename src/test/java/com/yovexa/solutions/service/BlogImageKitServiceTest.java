package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.blog.BlogRequest;
import com.yovexa.solutions.dto.blog.BlogResponse;
import com.yovexa.solutions.mapper.EntityMapper;
import com.yovexa.solutions.model.Blog;
import com.yovexa.solutions.repository.BlogRepository;
import com.yovexa.solutions.service.impl.BlogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogImageKitServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    private final EntityMapper mapper = new EntityMapper();

    private BlogServiceImpl blogService;

    @BeforeEach
    public void setUp() {
        blogService = new BlogServiceImpl(blogRepository, mapper, mongoTemplate);
    }

    private static final String IMAGEKIT_URL = "https://ik.imagekit.io/yovexa/blog-post.webp";

    @Test
    void testCreateBlog_WithDirectImageKitUrl() {
        BlogRequest request = BlogRequest.builder()
                .title("Modern Microservices Architecture")
                .excerpt("A guide to modern architectures.")
                .content("# Content")
                .category("WEB_APPLICATIONS")
                .author("Tech Lead")
                .featuredImage(IMAGEKIT_URL)
                .build();

        when(blogRepository.existsBySlug(any())).thenReturn(false);
        when(blogRepository.save(any(Blog.class))).thenAnswer(invocation -> {
            Blog b = invocation.getArgument(0);
            b.setId("blog-1");
            return b;
        });

        BlogResponse response = blogService.createBlog(request);

        assertNotNull(response);
        assertEquals(IMAGEKIT_URL, response.getFeaturedImage());
        assertEquals(IMAGEKIT_URL, response.getImage());
        verify(blogRepository).save(argThat(blog -> IMAGEKIT_URL.equals(blog.getFeaturedImage())));
    }

    @Test
    void testCreateBlog_WithQuotedAndSpacedImageKitUrl() {
        BlogRequest request = BlogRequest.builder()
                .title("Clean Architecture Patterns")
                .excerpt("Building maintainable software.")
                .content("### Clean Code")
                .category("WEB_APPLICATIONS")
                .author("Author")
                .featuredImage("  \"" + IMAGEKIT_URL + "\"  ")
                .build();

        when(blogRepository.existsBySlug(any())).thenReturn(false);
        when(blogRepository.save(any(Blog.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BlogResponse response = blogService.createBlog(request);

        assertNotNull(response);
        assertEquals(IMAGEKIT_URL, response.getFeaturedImage());
        assertEquals(IMAGEKIT_URL, response.getImage());
    }

    @Test
    void testCreateBlog_WithImageKitJsonObjectString() {
        String jsonPayload = "{\"fileId\":\"ik_file_001\",\"url\":\"" + IMAGEKIT_URL + "\"}";

        BlogRequest request = BlogRequest.builder()
                .title("Advanced Reactive Streams")
                .excerpt("Reactive programming fundamentals.")
                .content("Content here")
                .category("WEB_APPLICATIONS")
                .author("Staff Engineer")
                .featuredImage(jsonPayload)
                .build();

        when(blogRepository.existsBySlug(any())).thenReturn(false);
        when(blogRepository.save(any(Blog.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BlogResponse response = blogService.createBlog(request);

        assertNotNull(response);
        assertEquals(IMAGEKIT_URL, response.getFeaturedImage());
        assertEquals(IMAGEKIT_URL, response.getImage());
    }

    @Test
    void testUpdateBlog_WithImageKitUrl() {
        String blogId = "blog-123";
        Blog existingBlog = Blog.builder()
                .id(blogId)
                .title("Existing Blog")
                .slug("existing-blog")
                .excerpt("Old excerpt")
                .content("Old content")
                .category("MOBILE_APPS")
                .featuredImage("https://old-domain.com/old-image.jpg")
                .build();

        when(blogRepository.findById(blogId)).thenReturn(Optional.of(existingBlog));
        when(blogRepository.save(any(Blog.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BlogRequest updateRequest = BlogRequest.builder()
                .title("Updated Blog Title")
                .slug("existing-blog")
                .excerpt("Updated excerpt")
                .content("Updated content")
                .category("MOBILE_APPS")
                .featuredImage(IMAGEKIT_URL)
                .build();

        BlogResponse response = blogService.updateBlog(blogId, updateRequest);

        assertNotNull(response);
        assertEquals(IMAGEKIT_URL, response.getFeaturedImage());
        assertEquals(IMAGEKIT_URL, response.getImage());
        verify(blogRepository).save(argThat(blog -> IMAGEKIT_URL.equals(blog.getFeaturedImage())));
    }

    @Test
    void testUpdateBlog_PreservesExistingImageWhenNotProvided() {
        String blogId = "blog-456";
        String existingImageUrl = "https://ik.imagekit.io/yovexa/existing-image.png";
        Blog existingBlog = Blog.builder()
                .id(blogId)
                .title("Existing Blog")
                .slug("existing-blog-slug")
                .excerpt("Old excerpt")
                .content("Old content")
                .category("MOBILE_APPS")
                .featuredImage(existingImageUrl)
                .build();

        when(blogRepository.findById(blogId)).thenReturn(Optional.of(existingBlog));
        when(blogRepository.save(any(Blog.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BlogRequest updateRequest = BlogRequest.builder()
                .title("Updated Blog Title")
                .slug("existing-blog-slug")
                .excerpt("Updated excerpt")
                .content("Updated content")
                .category("MOBILE_APPS")
                .featuredImage(null) // Unchanged / not sent
                .build();

        BlogResponse response = blogService.updateBlog(blogId, updateRequest);

        assertNotNull(response);
        assertEquals(existingImageUrl, response.getFeaturedImage());
        assertEquals(existingImageUrl, response.getImage());
    }

    @Test
    void testGetPublicBlogs_WithCategoryWebApplications() {
        Blog b1 = Blog.builder()
                .id("1")
                .title("Spring Boot Microservices")
                .excerpt("Building scalable microservices.")
                .content("Content 1")
                .category("WEB_APPLICATIONS")
                .status("PUBLISHED")
                .build();
        Blog b2 = Blog.builder()
                .id("2")
                .title("Flutter Mobile UI")
                .excerpt("Building mobile apps.")
                .content("Content 2")
                .category("MOBILE_APPS")
                .status("PUBLISHED")
                .build();

        when(blogRepository.findByStatusOrderByPublishedAtDesc("PUBLISHED")).thenReturn(List.of(b1, b2));

        List<BlogResponse> result = blogService.getPublicBlogs(null, "WEB_APPLICATIONS");
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("WEB_APPLICATIONS", result.get(0).getCategory());
    }

    @Test
    void testGetPublicBlogs_WithVariedCategoryFormats() {
        Blog b1 = Blog.builder()
                .id("1")
                .title("Web App Dev")
                .category("WEB_APPLICATIONS")
                .status("PUBLISHED")
                .build();

        when(blogRepository.findByStatusOrderByPublishedAtDesc("PUBLISHED")).thenReturn(List.of(b1));

        assertEquals(1, blogService.getPublicBlogs(null, "web_applications").size());
        assertEquals(1, blogService.getPublicBlogs(null, "Web Applications").size());
        assertEquals(1, blogService.getPublicBlogs(null, "web-applications").size());
        assertEquals(1, blogService.getPublicBlogs(null, "all").size());
        assertEquals(1, blogService.getPublicBlogs(null, null).size());
    }
}
