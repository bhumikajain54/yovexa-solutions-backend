package com.yovexa.solutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class YovexaSolutionsBackendApplication {

    public static void main(String[] args) {
        loadDotenvIfPresent();
        SpringApplication.run(YovexaSolutionsBackendApplication.class, args);
    }

    private static void loadDotenvIfPresent() {
        java.io.File envFile = new java.io.File(".env");
        if (!envFile.exists() || !envFile.isFile()) {
            return;
        }
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(envFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#") || !line.contains("=")) {
                    continue;
                }
                int idx = line.indexOf('=');
                String key = line.substring(0, idx).trim();
                String value = line.substring(idx + 1).trim();
                if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1);
                }
                if (System.getProperty(key) == null && System.getenv(key) == null) {
                    System.setProperty(key, value);
                }
                String upperKey = key.toUpperCase();
                if (System.getProperty(upperKey) == null && System.getenv(upperKey) == null) {
                    System.setProperty(upperKey, value);
                }
                if (key.equalsIgnoreCase("Imagekit_Private")) {
                    if (System.getProperty("IMAGEKIT_PRIVATE_KEY") == null && System.getenv("IMAGEKIT_PRIVATE_KEY") == null) {
                        System.setProperty("IMAGEKIT_PRIVATE_KEY", value);
                    }
                }
                if (key.equalsIgnoreCase("Imagekit_Public")) {
                    if (System.getProperty("IMAGEKIT_PUBLIC_KEY") == null && System.getenv("IMAGEKIT_PUBLIC_KEY") == null) {
                        System.setProperty("IMAGEKIT_PUBLIC_KEY", value);
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
}
