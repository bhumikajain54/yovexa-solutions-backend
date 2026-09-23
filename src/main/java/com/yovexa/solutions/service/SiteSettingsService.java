package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.settings.SiteSettingsRequest;
import com.yovexa.solutions.dto.settings.SiteSettingsResponse;

public interface SiteSettingsService {
    SiteSettingsResponse getSettings();
    SiteSettingsResponse updateSettings(SiteSettingsRequest request);
}
