package com.criteo.models.out;

import com.criteo.models.internal.Campaign;
import com.criteo.models.internal.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
public class CampaignOutModel {

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private BigDecimal bid;
    private List<ProductOutModel> products;

    public static CampaignOutModel of(Campaign campaign, List<Product> campaignProducts) {

        return CampaignOutModel.builder()
                .name(campaign.getName())
                .startDate(campaign.getStartDate())
                .endDate(campaign.getEndDate())
                .bid(campaign.getBid())
                .products(ProductOutModel.of(campaignProducts))
                .build();
    }

}
