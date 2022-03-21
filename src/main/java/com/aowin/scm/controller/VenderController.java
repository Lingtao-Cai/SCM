package com.aowin.scm.controller;

import com.aowin.scm.pojo.Product;
import com.aowin.scm.pojo.QueryResult;
import com.aowin.scm.pojo.Result;
import com.aowin.scm.pojo.Vender;
import com.aowin.scm.service.VenderService;
import com.aowin.scm.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VenderController {
    @Autowired
    private VenderService venderService;

    @RequestMapping("venders")
    public QueryResult getAllVenders(Integer currentPage, Integer pageSize, HttpServletRequest request){
        Page page = new Page(currentPage, pageSize);
        int total = venderService.countAll();
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        List<Vender> list = venderService.findAll(params);
        return QueryResult.ok(total, list);
    }

    @PostMapping("addVender")
    public Result addVender(Vender vender){
        if (venderService.findVenderByCode(vender.getVenderCode()) != null){
            return Result.error("供应商编号已存在");
        }else {
            venderService.addVender(vender);
            return Result.ok(null, "添加vender成功");
        }
    }

    @PostMapping("removeVender")
    public Result removeVender(String venderCode){
        venderService.removeVender(venderCode);
        return Result.ok(null, "删除vender成功");
    }

    @PostMapping("updateVender")
    public Result updateVender(Vender vender){
        venderService.updateVender(vender);
        return Result.ok(null, "修改vender成功");
    }

    @PostMapping("findVenderByCode")
    public Result findVenderByCode(String venderCode){
        Vender vender = venderService.findVenderByCode(venderCode);
        return Result.ok(vender);
    }

    @RequestMapping("searchVender")
    public QueryResult searchVender(Integer currentPage, Integer pageSize, Vender vender){
        Page page = new Page(currentPage, pageSize);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
//        params.put("vender",vender);
        params.put("venderCode",vender.getVenderCode());
        System.out.println("vendercode= "+vender.getVenderCode());
        params.put("name",vender.getName());
        params.put("contactor",vender.getContactor());
        params.put("address",vender.getAddress());
        params.put("postcode",vender.getPostcode());
        params.put("tel",vender.getTel());
        params.put("fax",vender.getFax());
        params.put("createDate",vender.getCreateDate());
        List<Vender> list = venderService.searchVender(params);
        int total = venderService.countAllResults(params);
        //total = venderService.countAll();
        //List<Vender> list = venderService.findAll(params);
        return QueryResult.ok(total, list);
//        List<Vender> list = venderService.searchVender(vender);
//        if (list.isEmpty()){
//            return Result.ok(null, "没有查询到结果");
//        }else {
//            return Result.ok(list, "查询成功");
//        }

    }

    @RequestMapping("findAllVender")
    public Result findAllVender(){
        List<Vender> list = venderService.findAllVender();
        return Result.ok(list);
    }

}
