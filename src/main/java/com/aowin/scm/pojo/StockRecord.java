package com.aowin.scm.pojo;

import lombok.Data;

@Data
public class StockRecord {
    private int stockId;
    private String productCode;
    private String orderCode;
    private int stockNum;
    private int stockType;
    private String stockTime;
    private String createUser;
}
