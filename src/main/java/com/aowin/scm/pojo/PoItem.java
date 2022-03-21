package com.aowin.scm.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PoItem {
    private BigDecimal poId;
    private String productCode;
    private float unitPrice;
    private int num;
    private String unitName;
    private float itemPrice;
    private String productName;
}
