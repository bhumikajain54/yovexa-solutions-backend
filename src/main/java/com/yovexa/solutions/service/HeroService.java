package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.hero.HeroRequest;
import com.yovexa.solutions.dto.hero.HeroResponse;

import java.util.List;

public interface HeroService {
    List<HeroResponse> getAllHeroes();
    HeroResponse getHeroById(String id);
    HeroResponse getActiveHero();
    HeroResponse createHero(HeroRequest request);
    HeroResponse updateHero(String id, HeroRequest request);
    void deleteHero(String id);
}
