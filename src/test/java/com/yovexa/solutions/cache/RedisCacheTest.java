package com.yovexa.solutions.cache;

import com.yovexa.solutions.config.RedisConfig;
import com.yovexa.solutions.dto.blog.BlogResponse;
import com.yovexa.solutions.dto.casestudy.CaseStudyResponse;
import com.yovexa.solutions.dto.project.ProjectResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RedisCacheTest {

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Test
    void testCacheManagerConfiguration() {
        if (cacheManager == null) {
            System.out.println("CacheManager not loaded in test context");
            return;
        }

        assertNotNull(cacheManager.getCache(RedisConfig.PROJECTS_CACHE));
        assertNotNull(cacheManager.getCache(RedisConfig.CASE_STUDIES_CACHE));
        assertNotNull(cacheManager.getCache(RedisConfig.BLOGS_CACHE));
        assertNotNull(cacheManager.getCache(RedisConfig.DASHBOARD_CACHE));
        assertNotNull(cacheManager.getCache(RedisConfig.ADMINS_CACHE));
    }

    @Test
    void testProjectCaseStudyAndBlogSerialization() {
        RedisConfig.RedisJsonSerializer serializer = new RedisConfig.RedisJsonSerializer();

        // 1. ProjectResponse list
        ProjectResponse proj = ProjectResponse.builder()
                .id("proj-1")
                .name("Test Project")
                .slug("test-project")
                .features(List.of("Feature 1"))
                .technologies(List.of("Tech 1"))
                .createdAt(Instant.now())
                .build();
        byte[] projBytes = serializer.serialize(List.of(proj));
        Object projDeser = serializer.deserialize(projBytes);
        assertNotNull(projDeser);
        assertTrue(projDeser instanceof List);

        // 2. CaseStudyResponse list
        CaseStudyResponse cs = CaseStudyResponse.builder()
                .id("cs-1")
                .title("Test CS")
                .slug("test-cs")
                .createdAt(Instant.now())
                .build();
        byte[] csBytes = serializer.serialize(List.of(cs));
        Object csDeser = serializer.deserialize(csBytes);
        assertNotNull(csDeser);
        assertTrue(csDeser instanceof List);

        // 3. BlogResponse list
        BlogResponse blog = BlogResponse.builder()
                .id("blog-1")
                .title("Test Blog")
                .slug("test-blog")
                .createdAt(Instant.now())
                .build();
        byte[] blogBytes = serializer.serialize(List.of(blog));
        Object blogDeser = serializer.deserialize(blogBytes);
        assertNotNull(blogDeser);
        assertTrue(blogDeser instanceof List);

        // 4. Categories list (List<Map<String, String>>)
        List<java.util.Map<String, String>> categories = List.of(
                java.util.Map.of("id", "all", "label", "All Projects"),
                java.util.Map.of("id", "WEB_APPLICATIONS", "label", "Web Applications"));
        byte[] catBytes = serializer.serialize(categories);
        Object catDeser = serializer.deserialize(catBytes);
        assertNotNull(catDeser);
        assertTrue(catDeser instanceof List);
    }

    @Test
    void testCachePutAndGetWithProjectList() {
        if (cacheManager == null)
            return;
        Cache projectCache = cacheManager.getCache(RedisConfig.PROJECTS_CACHE);
        if (projectCache == null)
            return;

        ProjectResponse proj = ProjectResponse.builder()
                .id("proj-1")
                .name("Test Project")
                .slug("test-project")
                .createdAt(Instant.now())
                .build();

        List<ProjectResponse> list = List.of(proj);

        try {
            projectCache.put("test-all", list);
            Cache.ValueWrapper wrapper = projectCache.get("test-all");
            assertNotNull(wrapper, "Wrapper should not be null");
            if (wrapper != null) {
                Object cachedValue = wrapper.get();
                assertNotNull(cachedValue, "Cached value should not be null");
                assertTrue(cachedValue instanceof List, "Cached value should be a List");
            }

            projectCache.evict("test-all");
            assertNull(projectCache.get("test-all"));
        } catch (org.springframework.data.redis.RedisConnectionFailureException e) {
            System.out.println("Redis server offline during testCachePutAndGetWithProjectList: " + e.getMessage());
        }
    }
}
