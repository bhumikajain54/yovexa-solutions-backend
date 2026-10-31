package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.casestudy.CaseStudyRequest;
import com.yovexa.solutions.dto.casestudy.CaseStudyResponse;
import com.yovexa.solutions.mapper.EntityMapper;
import com.yovexa.solutions.model.CaseStudy;
import com.yovexa.solutions.repository.CaseStudyRepository;
import com.yovexa.solutions.service.impl.CaseStudyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CaseStudyImageKitServiceTest {

    @Mock
    private CaseStudyRepository caseStudyRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    private final EntityMapper mapper = new EntityMapper();

    private CaseStudyServiceImpl caseStudyService;

    @BeforeEach
    public void setUp() {
        caseStudyService = new CaseStudyServiceImpl(caseStudyRepository, mapper, mongoTemplate);
    }

    private static final String IMAGEKIT_URL = "https://ik.imagekit.io/yovexa/casestudy-arch.webp";

    @Test
    void testCreateCaseStudy_WithDirectImageKitUrl() {
        CaseStudyRequest request = CaseStudyRequest.builder()
                .title("Enterprise Order Sync Engine")
                .summary("Real-time telemetry and order sync architecture.")
                .problem("High database replication latency.")
                .solution("Implemented distributed event store.")
                .category("WEB_APPLICATIONS")
                .featuredImage(IMAGEKIT_URL)
                .build();

        when(caseStudyRepository.existsBySlug(any())).thenReturn(false);
        when(caseStudyRepository.save(any(CaseStudy.class))).thenAnswer(invocation -> {
            CaseStudy cs = invocation.getArgument(0);
            cs.setId("cs-1");
            return cs;
        });

        CaseStudyResponse response = caseStudyService.createCaseStudy(request);

        assertNotNull(response);
        assertEquals(IMAGEKIT_URL, response.getFeaturedImage());
        assertEquals(IMAGEKIT_URL, response.getImage());
        verify(caseStudyRepository).save(argThat(cs -> IMAGEKIT_URL.equals(cs.getFeaturedImage())));
    }

    @Test
    void testCreateCaseStudy_WithQuotedAndSpacedImageKitUrl() {
        CaseStudyRequest request = CaseStudyRequest.builder()
                .title("Cloud Infrastructure Modernization")
                .summary("Containerized cluster migration.")
                .problem("Legacy monolithic VM infrastructure.")
                .solution("Multi-region Kubernetes deployment.")
                .category("WEB_APPLICATIONS")
                .featuredImage("  '" + IMAGEKIT_URL + "'  ")
                .build();

        when(caseStudyRepository.existsBySlug(any())).thenReturn(false);
        when(caseStudyRepository.save(any(CaseStudy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CaseStudyResponse response = caseStudyService.createCaseStudy(request);

        assertNotNull(response);
        assertEquals(IMAGEKIT_URL, response.getFeaturedImage());
        assertEquals(IMAGEKIT_URL, response.getImage());
    }

    @Test
    void testCreateCaseStudy_WithImageKitJsonObjectString() {
        String jsonPayload = "{\"fileId\":\"cs_img_777\",\"url\":\"" + IMAGEKIT_URL + "\"}";

        CaseStudyRequest request = CaseStudyRequest.builder()
                .title("FinTech Payment Processing Hub")
                .summary("Sub-second payment gateway reconciliation.")
                .problem("Manual reconciliation backlog.")
                .solution("Automated distributed consensus ledger.")
                .category("WEB_APPLICATIONS")
                .featuredImage(jsonPayload)
                .build();

        when(caseStudyRepository.existsBySlug(any())).thenReturn(false);
        when(caseStudyRepository.save(any(CaseStudy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CaseStudyResponse response = caseStudyService.createCaseStudy(request);

        assertNotNull(response);
        assertEquals(IMAGEKIT_URL, response.getFeaturedImage());
        assertEquals(IMAGEKIT_URL, response.getImage());
    }

    @Test
    void testUpdateCaseStudy_WithImageKitUrl() {
        String csId = "cs-999";
        CaseStudy existing = CaseStudy.builder()
                .id(csId)
                .title("Existing Case Study")
                .slug("existing-case-study")
                .summary("Initial summary")
                .problem("Initial problem")
                .solution("Initial solution")
                .category("WEB_APPLICATIONS")
                .featuredImage("https://old-domain.com/diagram.png")
                .build();

        when(caseStudyRepository.findById(csId)).thenReturn(Optional.of(existing));
        when(caseStudyRepository.save(any(CaseStudy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CaseStudyRequest updateRequest = CaseStudyRequest.builder()
                .title("Updated Case Study Title")
                .slug("existing-case-study")
                .summary("Updated summary")
                .problem("Updated problem")
                .solution("Updated solution")
                .category("WEB_APPLICATIONS")
                .featuredImage(IMAGEKIT_URL)
                .build();

        CaseStudyResponse response = caseStudyService.updateCaseStudy(csId, updateRequest);

        assertNotNull(response);
        assertEquals(IMAGEKIT_URL, response.getFeaturedImage());
        assertEquals(IMAGEKIT_URL, response.getImage());
        verify(caseStudyRepository).save(argThat(cs -> IMAGEKIT_URL.equals(cs.getFeaturedImage())));
    }

    @Test
    void testUpdateCaseStudy_PreservesExistingImageWhenNotProvided() {
        String csId = "cs-888";
        String existingImageUrl = "https://ik.imagekit.io/yovexa/preserved-case-study.jpg";
        CaseStudy existing = CaseStudy.builder()
                .id(csId)
                .title("Existing Case Study")
                .slug("preserved-case-study")
                .summary("Summary")
                .problem("Problem")
                .solution("Solution")
                .category("WEB_APPLICATIONS")
                .featuredImage(existingImageUrl)
                .build();

        when(caseStudyRepository.findById(csId)).thenReturn(Optional.of(existing));
        when(caseStudyRepository.save(any(CaseStudy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CaseStudyRequest updateRequest = CaseStudyRequest.builder()
                .title("Updated Title")
                .slug("preserved-case-study")
                .summary("Summary")
                .problem("Problem")
                .solution("Solution")
                .category("WEB_APPLICATIONS")
                .featuredImage(null) // Unchanged
                .build();

        CaseStudyResponse response = caseStudyService.updateCaseStudy(csId, updateRequest);

        assertNotNull(response);
        assertEquals(existingImageUrl, response.getFeaturedImage());
        assertEquals(existingImageUrl, response.getImage());
    }
}
