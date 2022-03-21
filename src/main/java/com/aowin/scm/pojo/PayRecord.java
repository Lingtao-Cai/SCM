package com.aowin.scm.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayRecord {
    private int record_id;
    private String pay_time;
    private BigDecimal pay_price;
    private String account;
    private String orderCode;
    private int pay_type;
}
