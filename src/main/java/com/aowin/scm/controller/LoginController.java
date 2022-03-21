package com.aowin.scm.controller;

import com.aowin.scm.pojo.LoginResult;
import com.aowin.scm.pojo.ScmUser;
import com.aowin.scm.service.ScmUserService;
import com.aowin.scm.service.impl.ScmUserServiceImpl;
import com.aowin.scm.utils.jwt.JwtUtils;
import com.aowin.scm.utils.jwt.PassToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Autowired
    private ScmUserService scmUserService;


    @PassToken
    @RequestMapping("/login")
    public LoginResult login(String account, String password) {
        System.out.println(account);
        System.out.println(password);

        LoginResult r = new LoginResult();
        ScmUser scmUser = scmUserService.findUserByAccount(account);
        if (scmUser.getPassword().equals(password)){
            String token = "Bearer " + JwtUtils.createToken(scmUser.getAccount(), scmUser.getName(), 60 * 60 * 2);
            r.setCode(200);
            r.setMsg("成功");
            r.setToken(token);
            return r;
        }else {
            r.setCode(401);
            r.setMsg("用户不存在或密码错误");
            r.setToken(null);
            return r;
        }
//        LoginResult r = new LoginResult();
//        String token = "Bearer " + JwtUtils.createToken(3, "zhangsan", 60 * 60 * 2);
//            r.setCode(200);
//            r.setMsg("成功");
//            r.setToken(token);
//            return r;
    }

}
