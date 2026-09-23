package com.yovexa.solutions.repository;

import com.yovexa.solutions.model.SiteSettings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteSettingsRepository extends MongoRepository<SiteSettings, String> {
    default Optional<SiteSettings> getSettings() {
        return findById("default_settings");
    }
}
