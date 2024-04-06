package com.criteo.controllers;

import com.criteo.models.in.ProductInModel;
import com.criteo.models.internal.Campaign;
import com.criteo.models.internal.Product;
import com.criteo.models.out.CampaignOutModel;
import com.criteo.models.out.ProductOutModel;
import com.criteo.models.out.ValidResponse;
import com.criteo.services.CampaignService;
import com.criteo.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SellerController {

    private final CampaignService campaignService;
    private final ProductService productService;

    @PostMapping(path = "/seller/create-campaign",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ValidResponse createCampaign(@RequestParam(name = "name")
                                        @NotBlank(message = "Campaign name is missing.") String name,
                                        @RequestParam(name = "startDate")
                                        @NotNull(message = "Campaign startDate is missing.")
                                        @FutureOrPresent(message = "Bad Campaign startDate.")
                                        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date startDate,
                                        @RequestParam(name = "bid")
                                        @NotNull(message = "Campaign bid is missing.") BigDecimal bid,
                                        @RequestBody @NotEmpty(message = "Campaign products are missing.")
                                        @Valid List<ProductInModel> products) {

        Campaign campaign = campaignService.createCampaign(name, startDate, bid, products);
        List<Product> campaignListOfProducts = productService.getProductsByCampaignId(campaign.getId());

        return ValidResponse.of(CampaignOutModel.builder()
                .name(campaign.getName())
                .startDate(campaign.getStartDate())
                .endDate(campaign.getEndDate())
                .bid(campaign.getBid())
                .build()
                .withProducts(ProductOutModel.of(campaignListOfProducts)));
    }

}
