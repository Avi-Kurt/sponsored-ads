package com.criteo.constants;

import com.criteo.models.in.ProductInModel;
import com.criteo.models.internal.Campaign;
import com.criteo.models.internal.Product;
import com.criteo.models.out.CampaignOutModel;
import com.criteo.models.out.ProductOutModel;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class TestConstants {

    public static final BigDecimal bid = new BigDecimal(100);

    public static final String testCategory = "testCategory";

    public static final Date startDate = Date.from(new Date().toInstant().plus(1, ChronoUnit.DAYS));

    public static final Date endDate = Date.from(startDate.toInstant().plus(10, ChronoUnit.DAYS));

    public static final String tooLongText = """
            Loremipsumdolorsitametconsectetuerad
            ipiscingelitAeneancommodoligulaegetdolorAeneanmassaCumsociisnatoquepenatibusetmagni
            sdisparturientmontesnasceturridiculusmusDonecquamfelisultriciesnecpellentesqueeupre
            tiumquissemNullasdasdasddaaconsequatmassaquisenimDonaAdwSec""";

    public static final Campaign campaign = Campaign.builder()
            .id(1L)
            .name("campaign_name")
            .startDate(startDate)
            .endDate(endDate)
            .bid(bid)
            .build();

    public static final Product product1 = Product.builder()
            .id(10L)
            .campaignId(1L)
            .title("product_1")
            .category("product_category")
            .price(BigDecimal.valueOf(300))
            .serialNumber("product_1_serial_number")
            .build();

    public static final Product product2 = Product.builder()
            .id(20L)
            .campaignId(1L)
            .title("product_2")
            .category("product_category")
            .price(BigDecimal.valueOf(400))
            .serialNumber("product_2_serial_number")
            .build();

    public static final ProductInModel productInModel1 = ProductInModel.builder()
            .title("product_1")
            .category("product_category")
            .price(BigDecimal.valueOf(300))
            .build();

    public static final ProductInModel productInModel2 = ProductInModel.builder()
            .title("product_2")
            .category("product_category")
            .price(BigDecimal.valueOf(400))
            .build();

    public static final List<ProductInModel> productInModelsList = List.of(productInModel1, productInModel2);

    public static final List<Product> productList = List.of(product1, product2);

    public static final CampaignOutModel campaignOutModel = CampaignOutModel.builder()
            .name(campaign.getName())
            .startDate(campaign.getStartDate())
            .endDate(campaign.getEndDate())
            .bid(campaign.getBid())
            .build()
            .withProducts(ProductOutModel.of(productList));

}
