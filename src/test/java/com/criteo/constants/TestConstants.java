package com.criteo.constants;

import com.criteo.models.internal.Product;

import java.math.BigDecimal;

/**
 * Common test Models
 */
public class TestConstants {

    public static final String testCategory = "testCategory";

    public static final String tooLongText = """
            Loremipsumdolorsitametconsectetuerad
            ipiscingelitAeneancommodoligulaegetdolorAeneanmassaCumsociisnatoquepenatibusetmagni
            sdisparturientmontesnasceturridiculusmusDonecquamfelisultriciesnecpellentesqueeupre
            tiumquissemNullasdasdasddaaconsequatmassaquisenimDonaAdwSec""";

    public static final Product product1 = Product.builder()
            .id(10L)
            .campaignId(1L)
            .title("highest_bid_active_promoted_product_by_category")
            .category("highest_bid_active_promoted_product_by_category_category")
            .price(BigDecimal.valueOf(100.0))
            .serialNumber("highest_bid_active_promoted_product_by_category")
            .build();

    public static final Product product2 = Product.builder()
            .id(20L)
            .campaignId(2L)
            .title("highest_bid_active_promoted_product")
            .category("highest_bid_active_promoted_product")
            .price(BigDecimal.valueOf(200.0))
            .serialNumber("highest_bid_active_promoted_product")
            .build();

}
