package com.criteo.models.internal;

import lombok.*;

import java.math.BigDecimal;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;
    private Long campaignId;
    private String title;
    private String category;
    private BigDecimal price;
    private String serialNumber;

}
