package com.aowin.scm.pojo;

import lombok.Data;

@Data
public class Customer {
    private String customerCode;
    private String name;
    private String password;
    private String contactor;
    private String address;
    private String postcode;
    private String tel;
    private String fax;
    private String createDate;

}
