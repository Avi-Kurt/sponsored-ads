package com.criteo.controllers;

import com.criteo.constants.TestConstants;
import com.criteo.services.CampaignService;
import com.criteo.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

@WebMvcTest(SellerController.class)
class SellerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @MockBean
    private CampaignService campaignService;

    @Test
    void shouldReturnCreatedCampaign() throws Exception {
        Mockito.when(campaignService.createCampaign(ArgumentMatchers.any(), ArgumentMatchers.any(),
                        ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(TestConstants.campaign);
        Mockito.when(productService.getProductsByCampaignId(ArgumentMatchers.any()))
                .thenReturn(TestConstants.productList);

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestConstants.productInModelsListAsJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));
    }

    @Test
    void shouldReturnBadRequestOnNoProducts() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnMissingName() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestConstants.productInModelsListAsJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnMissingStartDate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestConstants.productInModelsListAsJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnMissingBid() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestConstants.productInModelsListAsJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnNameTooLong() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.tooLongText +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestConstants.productInModelsListAsJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnStartDateInPast() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateInPastAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestConstants.productInModelsListAsJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnBidTooLow() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + new BigDecimal(-10))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestConstants.productInModelsListAsJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnMissingTitleInProductInModel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(List.of(TestConstants.product1.withTitle(null)))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnMissingCategoryInProductInModel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(List.of(TestConstants.product1.withCategory(null)))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnMissingPriceInProductInModel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(List.of(TestConstants.product1.withPrice(null)))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnTitleTooLongInProductInModel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(
                                List.of(TestConstants.product1.withTitle(TestConstants.tooLongText)))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnCategoryTooLongInProductInModel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(
                                List.of(TestConstants.product1.withCategory(TestConstants.tooLongText)))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnPriceTooLowInProductInModel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(
                                List.of(TestConstants.product1.withPrice(BigDecimal.valueOf(-10))))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnPriceTooHighInProductInModel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "/seller/create-campaign?name=" + TestConstants.campaign.getName() +
                                        "&startDate=" + TestConstants.startDateAsString +
                                        "&bid=" + TestConstants.campaign.getBid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(
                                List.of(TestConstants.product1.withPrice(BigDecimal.valueOf(999999.99))))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

}
