package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.about.AboutRequest;
import com.yovexa.solutions.dto.about.AboutResponse;

import java.util.List;

public interface AboutService {
    List<AboutResponse> getAllAboutSections();
    AboutResponse getAboutById(String id);
    AboutResponse getActiveAbout();
    AboutResponse createAbout(AboutRequest request);
    AboutResponse updateAbout(String id, AboutRequest request);
    void deleteAbout(String id);
}
