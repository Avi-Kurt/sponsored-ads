package com.criteo.models.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Product {

    private Long id;
    private Long campaignId;
    private String title;
    private String category;
    private BigDecimal price;
    private String serialNumber;

}
