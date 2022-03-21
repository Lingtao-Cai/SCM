package com.aowin.scm.controller;

import com.aowin.scm.pojo.*;
import com.aowin.scm.service.StockRecordService;
import com.aowin.scm.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StockRecordController {
    @Autowired
    private StockRecordService stockRecordService;

    @RequestMapping("findStockRecords")
    public Result findStockRecords(String productCode){
//        Page page = new Page(currentPage, pageSize);
//        int total = stockRecordService.countAllRecords(productCode);
        Map<String, Object> params = new HashMap<>();
//        params.put("first", page.getFirst());
//        params.put("max", page.getMax());
        params.put("productCode", productCode);
        List<StockRecord> list = stockRecordService.findStockRecords(params);
        return Result.ok(list);
    }


    @RequestMapping("searchStockInByMonth")
    public QueryResult searchStockInByMonth(Integer currentPage, Integer pageSize, DateRange dateRange){
        Page page = new Page(currentPage, pageSize);
        int total = stockRecordService.countStockInByMonth(dateRange);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("startDate", dateRange.getStartDate());
        params.put("endDate", dateRange.getEndDate());
        List<StockRecord> list = stockRecordService.searchStockInByMonth(params);
        return QueryResult.ok(total, list);
    }

    @RequestMapping("searchStockOutByMonth")
    public QueryResult searchStockOutByMonth(Integer currentPage, Integer pageSize, DateRange dateRange){
        Page page = new Page(currentPage, pageSize);
        int total = stockRecordService.countStockOutByMonth(dateRange);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("startDate", dateRange.getStartDate());
        params.put("endDate", dateRange.getEndDate());
        List<StockRecord> list = stockRecordService.searchStockOutByMonth(params);
        return QueryResult.ok(total, list);
    }

}
