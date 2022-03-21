package com.aowin.scm.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PoMain {
    private BigDecimal poId;
    private String venderCode;
    private String account;
    private String createTime;
    private float tipFee;
    private float productTotal;
    private float poTotal;
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

    private String venderName;
    private List<PoItem> poItems;
    private float unpaidFee;
}
