package com.criteo.controllers;

import com.criteo.models.in.ProductInModel;
import com.criteo.models.internal.Campaign;
import com.criteo.models.internal.Product;
import com.criteo.services.CampaignService;
import com.criteo.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@WebMvcTest(SellerController.class)
class SellerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @MockBean
    private CampaignService campaignService;

    private static BigDecimal bid;
    private static String campaignName;
    private static Date startDate;
    private static String startDateAsString;
    private static Campaign campaign;
    private static List<ProductInModel> productInModels;
    private static String productInModelsListAsJson;
    private static List<Product> productList;

    @BeforeAll
    static void initBeforeAll() throws JsonProcessingException {
        bid = BigDecimal.valueOf(100L);
        campaignName = "new_campaign";
        startDate = Date.from(new Date().toInstant().plus(1, ChronoUnit.DAYS));
        startDateAsString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(startDate);

        campaign = Campaign.builder()
                .id(1L)
                .name(campaignName)
                .startDate(startDate)
                .endDate(Date.from(startDate.toInstant().plus(10, ChronoUnit.DAYS)))
                .bid(bid)
                .build();

        productInModels = List.of(ProductInModel.builder()
                        .title("product_1")
                        .category("product_1_category")
                        .price(BigDecimal.valueOf(300))
                        .build(),
                ProductInModel.builder()
                        .title("product_2")
                        .category("product_2_category")
                        .price(BigDecimal.valueOf(400))
                        .build());

        productInModelsListAsJson = new ObjectMapper().writeValueAsString(productInModels);

        productList = List.of(
                Product.builder()
                        .id(1L)
                        .campaignId(1L)
                        .title("product_1")
                        .category("product_1_category")
                        .price(BigDecimal.valueOf(300))
                        .serialNumber("serial_1")
                        .build(),
                Product.builder()
                        .id(2L)
                        .campaignId(1L)
                        .title("product_2")
                        .category("product_2_category")
                        .price(BigDecimal.valueOf(400))
                        .serialNumber("serial_2")
                        .build());
    }

    @Test
    void shouldReturnCreatedCampaign() throws Exception {
        Mockito.when(campaignService.createCampaign(ArgumentMatchers.any(), ArgumentMatchers.any(),
                        ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(campaign);
        Mockito.when(productService.getProductsByCampaignId(ArgumentMatchers.any()))
                .thenReturn(productList);

        mockMvc.perform(MockMvcRequestBuilders.post("/seller/create-campaign?name=" + campaignName +
                                "&startDate=" + startDateAsString + "&bid=" + bid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(productInModelsListAsJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));
    }

    @Test
    void shouldReturnBadRequestOnNoProducts() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/seller/create-campaign?name=" + campaignName +
                                "&startDate=" + startDateAsString + "&bid=" + bid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnMissingRequestParams() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/seller/create-campaign?name=" +
                                "&startDate=" + startDateAsString + "&bid=" + bid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

}
