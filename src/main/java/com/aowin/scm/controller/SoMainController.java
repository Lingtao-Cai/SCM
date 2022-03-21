package com.aowin.scm.controller;

import com.aowin.scm.pojo.*;
import com.aowin.scm.service.SoMainService;
import com.aowin.scm.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SoMainController {

    @Autowired
    private SoMainService soMainService;


    @RequestMapping("soLists")
    public QueryResult findAll(Integer currentPage, Integer pageSize, HttpServletRequest request){
        String account = request.getAttribute("account").toString();
        Page page = new Page(currentPage, pageSize);
        int total = soMainService.countAll(account);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("account", account);
        List<SoMain> list = soMainService.findAll(params);
        return QueryResult.ok(total, list);
    }


    @RequestMapping("soItems")
    public Result findBySoId(Integer currentPage, Integer pageSize, BigDecimal soId){
        SoMain soMain = soMainService.findBySoId(soId);

        System.out.println(soMain.getSoItems());
        System.out.println("size=" + soMain.getSoItems().size());
        return Result.ok(soMain, "查询主表成功");
    }


    @PostMapping("updateSoMain")
    public Result updateSoMain(@RequestBody SoMain soMain){
        System.out.println(soMain);
//        System.out.println("list is" + poMain.getPoItems());
        soMainService.updateSoMain(soMain);;
//        System.out.println(poMain);
        return Result.ok();
    }


    @PostMapping("removeSoMain")
    public Result removeSoMain(BigDecimal soId){
        soMainService.removeSoMain(soId);
        return Result.ok("删除成功");
    }

    @RequestMapping("findBySoPayType")
    public Result findBySoPayType(SoMain soMain){
        List<SoMain> list = soMainService.findBySoPayType(soMain);
        return Result.ok(list);
    }

    @PostMapping("endSoMain")
    public Result endSoMain(SoMain soMain){
        soMainService.endSoMain(soMain);
        return Result.ok();
    }

    @PostMapping("addSoMain")
    public Result addSoMain(@RequestBody SoMain soMain){
        System.out.println(soMain);
        soMainService.addSoMain(soMain);
        return Result.error("添加成功");
    }

    @RequestMapping("querySoMain")
    public QueryResult querySoMain(Integer currentPage, Integer pageSize, QueryCondition queryCondition){
        if (queryCondition.getStartDate() == ""){
            queryCondition.setStartDate(null);
        }
        System.out.println(queryCondition);
        Page page = new Page(currentPage, pageSize);
        int total = soMainService.countAllByCondition(queryCondition);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("startDate", queryCondition.getStartDate());
        params.put("endDate", queryCondition.getEndDate());
        params.put("status", queryCondition.getStatus());
        params.put("soId", queryCondition.getSoId());
        List<SoMain> list = soMainService.queryByConditions(params);
        System.out.println("list is" + list);
        return QueryResult.ok(total, list);
    }

    @RequestMapping("findStockOutByPayType")
    public Result findStockOutByPayType(SoMain soMain){
        List<SoMain> list = soMainService.findStockOutByPayType(soMain);
        return Result.ok(list);
    }

    @PostMapping("stockSoMain")
    public Result stockSoMain(SoMain soMain){
        soMainService.stockSoMain(soMain);
        return Result.ok(null, "出库成功");
    }

    @RequestMapping("findIncomeSoMain")
    public QueryResult findIncomeSoMain(Integer currentPage, Integer pageSize,SoMain soMain){
        Page page = new Page(currentPage, pageSize);
        int total = soMainService.countIncomeSoMain(soMain);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("payType", soMain.getPayType());
        params.put("account", soMain.getAccount());
        List<SoMain> list = soMainService.findIncomeSoMain(params);
        return QueryResult.ok(total, list);
    }

    @PostMapping("receiveMoney")
    public Result receiveMoney(SoMain soMain){
        System.out.println("somain"+soMain);
        soMainService.receiveMoney(soMain);
        return Result.ok();
    }

    @RequestMapping("reportSoMain")
    public Result reportSoMain(DateRange dateRange){
        System.out.println(dateRange);
        SoMainResult soMainResult = soMainService.reportSoMainDetails(dateRange);
        System.out.println("result is" + soMainResult);
        return Result.ok(soMainResult);
    }
}
