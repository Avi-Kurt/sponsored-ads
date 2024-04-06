package com.criteo.models.internal;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private Long id;
    private Long campaignId;
    private String title;
    private String category;
    private BigDecimal price;
    private String serialNumber;

}
