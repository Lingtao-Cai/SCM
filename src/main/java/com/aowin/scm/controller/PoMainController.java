package com.aowin.scm.controller;

import com.aowin.scm.pojo.*;
import com.aowin.scm.service.PoMainService;
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
public class PoMainController {

    @Autowired
    private PoMainService poMainService;


    @RequestMapping("poLists")
    public QueryResult findAll(Integer currentPage, Integer pageSize, HttpServletRequest request){
        String account = request.getAttribute("account").toString();
        Page page = new Page(currentPage, pageSize);
        int total = poMainService.countAll(account);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("account", account);
        List<PoMain> list = poMainService.findAll(params);
        return QueryResult.ok(total, list);
    }


    @RequestMapping("items")
    public Result findByPoId(Integer currentPage, Integer pageSize, BigDecimal poId){
        PoMain poMain = poMainService.findByPoId(poId);
        System.out.println(poMain);
        return Result.ok(poMain, "查询主表成功");
    }


    @PostMapping("updatePoMain")
    public Result updatePoMain(@RequestBody PoMain poMain){
//        System.out.println("list is" + poMain.getPoItems());
        poMainService.updateMain(poMain);
//        System.out.println(poMain);
        return Result.ok();
    }


    @PostMapping("removePoMain")
    public Result removePoMain(BigDecimal poId){
        poMainService.removePoMain(poId);
        return Result.ok("删除成功");
    }

    @RequestMapping("findByPayType")
    public Result findByPayType(PoMain poMain){
        List<PoMain> list = poMainService.findByPayType(poMain);
        return Result.ok(list);
    }

    @PostMapping("endPoMain")
    public Result endPoMain(PoMain poMain){
        poMainService.endPoMain(poMain);
        return Result.ok();
    }

    @PostMapping("addPoMain")
    public Result addPoMain(@RequestBody PoMain poMain){
        System.out.println(poMain);
        poMainService.addPoMain(poMain);
        return Result.error("添加成功");
    }

    @RequestMapping("queryPoMain")
    public QueryResult queryPoMain(Integer currentPage, Integer pageSize,QueryCondition queryCondition){
        if (queryCondition.getStartDate() == ""){
            queryCondition.setStartDate(null);
        }
        System.out.println(queryCondition);
        Page page = new Page(currentPage, pageSize);
        int total = poMainService.countAllByCondition(queryCondition);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("startDate", queryCondition.getStartDate());
        params.put("endDate", queryCondition.getEndDate());
        params.put("status", queryCondition.getStatus());
        params.put("poId", queryCondition.getPoId());
        List<PoMain> list = poMainService.queryByConditions(params);
        System.out.println("list is" + list);
        return QueryResult.ok(total, list);
    }

    @RequestMapping("findStockPoByPayType")
    public Result findStockPoByPayType(PoMain poMain){
        List<PoMain> list = poMainService.findStockPoByPayType(poMain);
        return Result.ok(list);
    }

    @PostMapping("stockPoMain")
    public Result stockPoMain(PoMain poMain){
        System.out.println(poMain);
        poMainService.stockPoMain(poMain);
        return Result.ok(null, "入库成功");
    }

    @RequestMapping("findOutcomeSoMain")
    public QueryResult findIncomeSoMain(Integer currentPage, Integer pageSize,PoMain poMain){
        Page page = new Page(currentPage, pageSize);
        int total = poMainService.countOutcomePoMain(poMain);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("payType", poMain.getPayType());
        params.put("account", poMain.getAccount());
        List<SoMain> list = poMainService.findOutcomePoMain(params);
        return QueryResult.ok(total, list);
    }

    @PostMapping("payMoney")
    public Result receiveMoney(PoMain poMain){
        //System.out.println("pomain"+poMain);
        poMainService.payMoney(poMain);
        return Result.ok();
    }

    @RequestMapping("reportPoMainDetail")
    public QueryResult reportPoMainDetail(Integer currentPage, Integer pageSize, DateRange dateRange){
        Page page = new Page(currentPage, pageSize);
        int total = poMainService.countPoMainDetail(dateRange);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("startDate", dateRange.getStartDate());
        params.put("endDate", dateRange.getEndDate());
        List<PoMain> list = poMainService.reportPoMainDetail(params);
        return QueryResult.ok(total, list);
    }

    @RequestMapping("reportPoMainSum")
    public Result reportPoMainSum(DateRange dateRange){
        PoMainResult poMainResult = poMainService.reportPoMainSum(dateRange.getStartDate(), dateRange.getEndDate());
        return Result.ok(poMainResult);
    }
}
