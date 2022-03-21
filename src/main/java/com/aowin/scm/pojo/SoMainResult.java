package com.aowin.scm.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SoMainResult {
    private float soMainTotalPrice;
    private int endSoMainNum;
    private int totalSoMainNum;
    private float paidFee;
    private float unpaidFee;
    private List<SoMain> soMains;
}
