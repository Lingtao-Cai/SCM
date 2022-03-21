package com.aowin.scm.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryCondition {
//    private String startYear;
//    private String startMonth;
    private String startDate;
//    private String endYear;
//    private String endMonth;
    private String endDate;

    private BigDecimal soId;
    private BigDecimal poId;
    private Integer status;

    private String orderCode;
    private int pay_Type;
}
