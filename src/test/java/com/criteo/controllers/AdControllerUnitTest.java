package com.criteo.controllers;

import com.criteo.constants.TestConstants;
import com.criteo.models.out.ProductOutModel;
import com.criteo.models.out.ValidResponse;
import com.criteo.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class AdControllerUnitTest {

    private static AdController adController;

    @BeforeAll
    static void init() {
        ProductService productService = Mockito.mock(ProductService.class);
        adController = new AdController(productService);

        Mockito.when(productService.getHighestBidActivePromotedProductByCategory(TestConstants.testCategory))
                .thenReturn(Optional.of(TestConstants.product1));
        Mockito.when(productService.getHighestBidActivePromotedProductByCategory(TestConstants.tooLongText))
                .thenReturn(Optional.of(TestConstants.product1));
        Mockito.when(productService.getHighestBidActivePromotedProduct())
                .thenReturn(Optional.of(TestConstants.product2));
    }

    @Test
    void shouldServeByCategory() {

        Assertions.assertEquals(String.valueOf(ValidResponse.of(ProductOutModel.of(TestConstants.product1))),
                String.valueOf(adController.serveAd(TestConstants.testCategory)));
    }

    @Test
    void shouldReturnHighestBidActivePromotedProduct() {

        Assertions.assertEquals(String.valueOf(ValidResponse.of(ProductOutModel.of(TestConstants.product2))),
                String.valueOf(adController.serveAd("unexciting category")));
    }

    @Test
    void shouldReturnHighestBidActivePromotedProductOnMissingCategory() {

        Assertions.assertEquals(String.valueOf(ValidResponse.of(ProductOutModel.of(TestConstants.product2))),
                String.valueOf(adController.serveAd(null)));
    }

    @Test
    void shouldServeByCategoryEvenWithTooLongText() {

        Assertions.assertEquals(String.valueOf(ValidResponse.of(ProductOutModel.of(TestConstants.product1))),
                String.valueOf(adController.serveAd(TestConstants.tooLongText)));
    }

}
