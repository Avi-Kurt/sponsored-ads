package com.criteo.models.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor
public class Campaign {

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private BigDecimal bid;

}
