package com.aowin.scm.pojo;

import lombok.Data;

@Data
public class LoginResult {
    private int code;
    private String msg;
    private Object data;
    private String token;
}
