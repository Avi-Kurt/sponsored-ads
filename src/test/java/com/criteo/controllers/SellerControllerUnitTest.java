package com.criteo.controllers;

import com.criteo.constants.TestConstants;
import com.criteo.models.out.ValidResponse;
import com.criteo.services.CampaignService;
import com.criteo.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SellerControllerUnitTest {

    private static SellerController sellerController;

    @BeforeAll
    static void init() {
        CampaignService campaignService = Mockito.mock(CampaignService.class);
        ProductService productService = Mockito.mock(ProductService.class);
        sellerController = new SellerController(campaignService, productService);

        Mockito.when(campaignService.createCampaign(TestConstants.campaign.getName(), TestConstants.campaign.getStartDate(),
                        TestConstants.campaign.getBid(), TestConstants.productInModelsList))
                .thenReturn(TestConstants.campaign);
        Mockito.when(productService.getProductsByCampaignId(TestConstants.campaign.getId()))
                .thenReturn(TestConstants.productList);
    }

    @Test
    void shouldReturnCreatedCampaign() {

        Assertions.assertEquals(String.valueOf(ValidResponse.of(TestConstants.campaignOutModel)),
                String.valueOf(sellerController.createCampaign(
                        TestConstants.campaign.getName(),
                        TestConstants.campaign.getStartDate(),
                        TestConstants.campaign.getBid(),
                        TestConstants.productInModelsList)));
    }

}
