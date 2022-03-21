package com.aowin.scm.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ScmUser {
    private String account;
    private String password;
    private String name;
    private String createDate;
    private Integer status;
}
