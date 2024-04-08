package com.criteo.services;

import com.criteo.constants.TestConstants;
import com.criteo.daos.ProductDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

class ProductServiceUnitTest {

    private static ProductService productService;

    @BeforeAll
    static void init() {
        ProductDao productDao = Mockito.mock(ProductDao.class);
        productService = new ProductService(productDao);

        Mockito.when(productDao.createProductsForCampaign(TestConstants.campaign.getId(), TestConstants.productInModelsList))
                .thenReturn(Optional.of(1));
        Mockito.when(productDao.createProductsForCampaign(TestConstants.campaign.getId(), null))
                .thenReturn(Optional.of(0));
        Mockito.when(productDao.findProductsByCampaignId(TestConstants.campaign.getId()))
                .thenReturn(TestConstants.productList);
        Mockito.when(productDao.findProductsByCampaignId(null))
                .thenReturn(Collections.emptyList());
        Mockito.when(productDao.findHighestBidActivePromotedProductByCategory(TestConstants.productInModel1.getCategory()))
                .thenReturn(Optional.of(TestConstants.product1));
        Mockito.when(productDao.findHighestBidActivePromotedProductByCategory(null))
                .thenReturn(Optional.empty());
        Mockito.when(productDao.findHighestBidActivePromotedProduct())
                .thenReturn(Optional.of(TestConstants.product2));
    }

    @Test
    void shouldCreateProductsForCampaign() {

        Assertions.assertEquals(Optional.of(1),
                productService.createProductsForCampaign(TestConstants.campaign.getId(), TestConstants.productInModelsList));
    }

    @Test
    void shouldCreateZeroProductsForCampaign() {

        Assertions.assertEquals(Optional.of(0),
                productService.createProductsForCampaign(TestConstants.campaign.getId(), null));
    }

    @Test
    void shouldGetProductsByCampaignId() {

        Assertions.assertEquals(TestConstants.productList,
                productService.getProductsByCampaignId(TestConstants.campaign.getId()));
    }

    @Test
    void shouldGetEmptyListOfProductsByCampaignId() {

        Assertions.assertEquals(Collections.emptyList(),
                productService.getProductsByCampaignId(null));
    }

    @Test
    void shouldGetHighestBidActivePromotedProductByCategory() {

        Assertions.assertEquals(Optional.of(TestConstants.product1),
                productService.getHighestBidActivePromotedProductByCategory(TestConstants.product1.getCategory()));
    }

    @Test
    void shouldGetOptionalOfEmpty() {

        Assertions.assertEquals(Optional.empty(),
                productService.getHighestBidActivePromotedProductByCategory(null));
    }

    @Test
    void shouldGetHighestBidActivePromotedProduct() {

        Assertions.assertEquals(Optional.of(TestConstants.product2),
                productService.getHighestBidActivePromotedProduct());
    }

}
