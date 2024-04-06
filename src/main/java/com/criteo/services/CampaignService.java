package com.criteo.services;

import com.criteo.daos.CampaignDao;
import com.criteo.exceptions.FailedToCreateException;
import com.criteo.models.in.ProductInModel;
import com.criteo.models.internal.Campaign;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {

    @Value("${campaign.duration-in-days}")
    private Integer campaignDurationInDays;

    private final CampaignDao campaignDao;
    private final ProductService productService;

    @Transactional
    public Campaign createCampaign(String name, Date startDate, BigDecimal bid, List<ProductInModel> products) {

        Date campaignEndDate = Date.from(startDate.toInstant().plus(campaignDurationInDays, ChronoUnit.DAYS));
        Campaign campaign = campaignDao.createCampaign(name, startDate, campaignEndDate, bid)
                .orElseThrow(() -> new FailedToCreateException("Campaign creation failed."));

        productService.createProductsForCampaign(campaign.getId(), products)
                .orElseThrow(() -> new FailedToCreateException("Products creation for Campaign failed."));

        return campaign;
    }

}
