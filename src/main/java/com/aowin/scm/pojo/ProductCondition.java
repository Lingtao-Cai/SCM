package com.aowin.scm.pojo;

import lombok.Data;

@Data
public class ProductCondition {
    private String productCode;
    private String productName;
    private int numMax;
    private int numMin;
}
