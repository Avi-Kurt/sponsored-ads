package com.criteo.models.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.With;

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
    @With
    private List<ProductOutModel> products;

}
