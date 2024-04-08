package com.criteo.controllers;

import com.criteo.constants.TestConstants;
import com.criteo.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@WebMvcTest(AdController.class)
class AdControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void shouldServeByCategory() throws Exception {
        Mockito.when(productService.getHighestBidActivePromotedProductByCategory(TestConstants.testCategory))
                .thenReturn(Optional.of(TestConstants.product1));
        Mockito.when(productService.getHighestBidActivePromotedProduct())
                .thenReturn(Optional.of(TestConstants.product2));

        mockMvc.perform(MockMvcRequestBuilders.get("/ad/serve?category=" + TestConstants.testCategory))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists());
    }

    @Test
    void shouldReturnBadRequestOnMissingParameter() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/ad/serve"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

    @Test
    void shouldReturnBadRequestOnValidationFailed() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/ad/serve?category=" + TestConstants.tooLongText))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

}
