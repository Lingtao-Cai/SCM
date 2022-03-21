package com.aowin.scm.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SoItem {
    private BigDecimal soId;
    private String productCode;
    private float unitPrice;
    private int num;
    private String unitName;
    private float itemPrice;
    private String productName;
}
