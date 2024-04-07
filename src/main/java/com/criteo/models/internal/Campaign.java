package com.criteo.models.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private BigDecimal bid;

}
