package com.yovexa.solutions.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {

    public static final String PROJECTS_CACHE = "projects";
    public static final String CASE_STUDIES_CACHE = "caseStudies";
    public static final String BLOGS_CACHE = "blogs";
    public static final String DASHBOARD_CACHE = "dashboard";
    public static final String ADMINS_CACHE = "admins";

    @Bean
    public CacheManager cacheManager() {
        log.info("Initializing in-memory ConcurrentMapCacheManager");
        return new ConcurrentMapCacheManager(
                PROJECTS_CACHE,
                CASE_STUDIES_CACHE,
                BLOGS_CACHE,
                DASHBOARD_CACHE,
                ADMINS_CACHE
        );
    }
}
