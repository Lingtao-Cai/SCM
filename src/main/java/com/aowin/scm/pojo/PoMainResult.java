package com.aowin.scm.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PoMainResult {
    private List<PoMain> poMains;
    private float PoMainTotalPrice;
    private int endPoMainNum;
    private int totalPoMainNum;
    private float paidFee;
    private float unpaidFee;


}
