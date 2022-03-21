package com.aowin.scm.controller;

import com.alibaba.fastjson.JSONObject;
import com.aowin.scm.pojo.QueryResult;
import com.aowin.scm.pojo.Result;
import com.aowin.scm.pojo.ScmUser;
import com.aowin.scm.service.ModelService;
import com.aowin.scm.service.ScmUserService;
import com.aowin.scm.utils.Page;
import com.aowin.scm.utils.jwt.PassToken;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class ScmUserController {

    @Autowired
    private ScmUserService scmUserService;

    @Autowired
    private ModelService modelService;


    @RequestMapping("users")
    public QueryResult getAll(Integer currentPage, Integer pageSize, HttpServletRequest request) {

//        System.out.println(request.getAttribute("userId"));
//        System.out.println(request.getAttribute("username"));
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Page page = new Page(currentPage, pageSize);
        int total = scmUserService.countAll();
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        List<ScmUser> list = scmUserService.findAll(params);
        return QueryResult.ok(total, list);
    }


    @PostMapping("getModelName")
    public List<String> getModelName(String account){
        System.out.println("account = " + account);
        List<String> list = modelService.findModel(account);
        System.out.println("list=" + list);
        return list;
    }

    @PostMapping("findModelName")
    public String findModelName(String account){
        System.out.println("account = " + account);
        List<String> list = modelService.findModel(account);
        System.out.println("list=" + list);
        return JSONObject.toJSONString(list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("addUser")
    public Result addUser(ScmUser user, String[] userModel){

//        List<String> strings = Arrays.asList(userModel);

        //System.out.println("usermodel=" + strings);
        scmUserService.addUser(user);
        //int i = 1/0;
        for (String s: userModel){
            System.out.println("string=" + s);
            modelService.addModelAccount(user.getAccount(), s);
        }
        return Result.ok();
//        System.out.println("account="+user.getAccount());
//        modelService.addModelAccount(user.getAccount(), strings.get(0));
    }

    //@PassToken
    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("removeUser")
    public Result removeUser(String account, HttpServletRequest request){
        while (!modelService.findModel(account).isEmpty()){
            modelService.removeModel(account);
        }

        scmUserService.removeByAccount(account);

        Result result = new Result();
        result.setCode(200);
        result.setMsg("删除成功");
        result.setData(null);

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("updateUser")
    public Result updateUser(ScmUser user, String[] userModel){

        scmUserService.updateUser(user);

        while (!modelService.findModel(user.getAccount()).isEmpty()){
            modelService.removeModel(user.getAccount());
        }

        for (String s: userModel){
            System.out.println("string=" + s);
            modelService.addModelAccount(user.getAccount(), s);
        }
        return Result.ok();
    }


}
