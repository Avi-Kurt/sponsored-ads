package com.criteo.models.internal;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Campaign {

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private BigDecimal bid;

}
