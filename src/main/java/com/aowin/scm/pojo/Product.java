package com.aowin.scm.pojo;

import lombok.Data;

@Data
public class Product {
    private String productCode;
    private Integer categoryId;
    private String name;
    private String unitName;
    private float price;
    private String createDate;
    private String remark;
    private Integer num;
    private Integer poNum;
    private Integer soNum;
    private String categoryName;
}
