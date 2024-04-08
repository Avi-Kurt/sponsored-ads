package com.criteo.services;

import com.criteo.constants.TestConstants;
import com.criteo.daos.CampaignDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

class CampaignServiceUnitTest {

    private static CampaignDao campaignDao;
    private static ProductService productService;
    private static CampaignService campaignService;

    @BeforeAll
    static void init() {
        campaignDao = Mockito.mock(CampaignDao.class);
        productService = Mockito.mock(ProductService.class);
        campaignService = new CampaignService(campaignDao, productService);
        ReflectionTestUtils.setField(campaignService, "campaignDurationInDays", 10);

        Mockito.when(campaignDao.createCampaign(
                        TestConstants.campaign.getName(),
                        TestConstants.campaign.getStartDate(),
                        TestConstants.campaign.getEndDate(),
                        TestConstants.campaign.getBid()))
                .thenReturn(Optional.of(TestConstants.campaign));
        Mockito.when(campaignDao.createCampaign(
                        null,
                        TestConstants.campaign.getStartDate(),
                        TestConstants.campaign.getEndDate(),
                        TestConstants.campaign.getBid()))
                .thenThrow(new DataIntegrityViolationException("Constraint violation: NOT NULL"));
        Mockito.when(campaignDao.createCampaign(
                        TestConstants.campaign.getName(),
                        null,
                        TestConstants.campaign.getEndDate(),
                        TestConstants.campaign.getBid()))
                .thenThrow(new DataIntegrityViolationException("Constraint violation: NOT NULL"));
        Mockito.when(campaignDao.createCampaign(
                        TestConstants.campaign.getName(),
                        TestConstants.campaign.getStartDate(),
                        null,
                        TestConstants.campaign.getBid()))
                .thenThrow(new DataIntegrityViolationException("Constraint violation: NOT NULL"));
        Mockito.when(campaignDao.createCampaign(
                        TestConstants.campaign.getName(),
                        TestConstants.campaign.getStartDate(),
                        TestConstants.campaign.getEndDate(),
                        null))
                .thenThrow(new DataIntegrityViolationException("Constraint violation: NOT NULL"));
        Mockito.when(productService.createProductsForCampaign(
                        TestConstants.campaign.getId(),
                        TestConstants.productInModelsList))
                .thenReturn(Optional.of(TestConstants.productInModelsList.size()));
        Mockito.when(productService.createProductsForCampaign(
                        TestConstants.campaign.getId(),
                        null))
                .thenReturn(Optional.of(0));
    }

    @Test
    void shouldCreateCampaign() {

        Assertions.assertEquals(campaignService.createCampaign(
                        TestConstants.campaign.getName(),
                        TestConstants.campaign.getStartDate(),
                        TestConstants.campaign.getBid(),
                        TestConstants.productInModelsList),
                TestConstants.campaign);
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionOnMissingName() {
        DataIntegrityViolationException thrown = Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> campaignService.createCampaign(null,
                        TestConstants.campaign.getStartDate(),
                        TestConstants.campaign.getBid(),
                        TestConstants.productInModelsList), "Constraint violation: NOT NULL");

        Assertions.assertEquals("Constraint violation: NOT NULL", thrown.getMessage());
    }

    @Test
    void shouldThrowNullPointerExceptionOnMissingStartDate() {
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class,
                () -> campaignService.createCampaign(
                        TestConstants.campaign.getName(),
                        null,
                        TestConstants.campaign.getBid(),
                        TestConstants.productInModelsList), "NullPointerException");

        Assertions.assertEquals("Cannot invoke \"java.util.Date.toInstant()\" because \"startDate\" is null",
                thrown.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionOnMissingBid() {
        DataIntegrityViolationException thrown = Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> campaignService.createCampaign(
                        TestConstants.campaign.getName(),
                        TestConstants.campaign.getStartDate(),
                        null,
                        TestConstants.productInModelsList), "Constraint violation: NOT NULL");

        Assertions.assertEquals("Constraint violation: NOT NULL", thrown.getMessage());
    }

    @Test
    void shouldCreateCampaignWithNoProducts() {

        Assertions.assertEquals(campaignService.createCampaign(
                        TestConstants.campaign.getName(),
                        TestConstants.campaign.getStartDate(),
                        TestConstants.campaign.getBid(),
                        null),
                TestConstants.campaign);
    }

}
