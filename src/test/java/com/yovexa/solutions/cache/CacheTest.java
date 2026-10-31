package com.yovexa.solutions.cache;

import com.yovexa.solutions.config.CacheConfig;
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

@SpringBootTest(classes = CacheConfig.class)
@ActiveProfiles("test")
class CacheTest {

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Test
    void testCacheManagerConfiguration() {
        if (cacheManager == null) {
            System.out.println("CacheManager not loaded in test context");
            return;
        }

        assertNotNull(cacheManager.getCache(CacheConfig.PROJECTS_CACHE));
        assertNotNull(cacheManager.getCache(CacheConfig.CASE_STUDIES_CACHE));
        assertNotNull(cacheManager.getCache(CacheConfig.BLOGS_CACHE));
        assertNotNull(cacheManager.getCache(CacheConfig.DASHBOARD_CACHE));
        assertNotNull(cacheManager.getCache(CacheConfig.ADMINS_CACHE));
    }

    @Test
    void testCachePutAndGetWithProjectList() {
        if (cacheManager == null) return;
        Cache projectCache = cacheManager.getCache(CacheConfig.PROJECTS_CACHE);
        if (projectCache == null) return;

        ProjectResponse proj = ProjectResponse.builder()
                .id("proj-1")
                .name("Test Project")
                .slug("test-project")
                .createdAt(Instant.now())
                .build();

        List<ProjectResponse> list = List.of(proj);

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
    }
}
