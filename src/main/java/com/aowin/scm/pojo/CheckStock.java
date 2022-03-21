package com.aowin.scm.pojo;

import lombok.Data;

@Data
public class CheckStock {
    private int stockId;
    private String productCode;
    private int originNum;
    private int realNum;
    private String stockTime;
    private String createUser;
    private String description;
    private String type;
    private int changeNum;
}
