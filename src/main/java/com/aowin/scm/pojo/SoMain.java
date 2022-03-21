package com.aowin.scm.pojo;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SoMain {
    private BigDecimal soId;
    private String customerCode;
    private String account;
    private String createTime;
    private float tipFee;
    private float productTotal;
    private float soTotal;
    private String payType;
    private float prePayFee;
    private int status;
    private String remark;
    private String stockTime;
    private String stockUser;
    private String payTime;
    private String payUser;
    private String prePayTime;
    private String prePayUser;
    private String endTime;
    private String endUser;

    private String customerName;
    private List<SoItem> soItems;
    private float unpaidFee;

}
