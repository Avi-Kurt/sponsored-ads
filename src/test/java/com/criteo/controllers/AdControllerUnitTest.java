package com.criteo.controllers;

import com.criteo.models.internal.Product;
import com.criteo.services.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

@WebMvcTest(AdController.class)
class AdControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private static String category;
    private static Product product1;
    private static Product product2;
    private static String tooLongText;

    @BeforeAll
    static void initBeforeAll() {
        category = "testCategory";
        tooLongText = """
                Loremipsumdolorsitametconsectetuerad
                ipiscingelitAeneancommodoligulaegetdolorAeneanmassaCumsociisnatoquepenatibusetmagni
                sdisparturientmontesnasceturridiculusmusDonecquamfelisultriciesnecpellentesqueeupre
                tiumquissemNullasdasdasddaaconsequatmassaquisenimDonec""";

        product1 = Product.builder()
                .id(10L)
                .campaignId(1L)
                .title("highest_bid_active_promoted_product_by_category")
                .category("highest_bid_active_promoted_product_by_category_category")
                .price(BigDecimal.valueOf(100.0))
                .serialNumber("highest_bid_active_promoted_product_by_category")
                .build();

        product2 = Product.builder()
                .id(20L)
                .campaignId(2L)
                .title("highest_bid_active_promoted_product")
                .category("highest_bid_active_promoted_product")
                .price(BigDecimal.valueOf(200.0))
                .serialNumber("highest_bid_active_promoted_product")
                .build();
    }

    @Test
    void shouldServeByCategory() throws Exception {
        Mockito.when(productService.getHighestBidActivePromotedProductByCategory(category))
                .thenReturn(Optional.of(product1));
        Mockito.when(productService.getHighestBidActivePromotedProduct())
                .thenReturn(Optional.of(product2));

        mockMvc.perform(MockMvcRequestBuilders.get("/ad/serve?category=" + category))
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

        mockMvc.perform(MockMvcRequestBuilders.get("/ad/serve?category=" + tooLongText))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-12006"));
    }

}