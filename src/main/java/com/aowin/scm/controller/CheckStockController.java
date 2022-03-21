package com.aowin.scm.controller;

import com.aowin.scm.pojo.CheckStock;
import com.aowin.scm.pojo.DateRange;
import com.aowin.scm.pojo.QueryResult;
import com.aowin.scm.pojo.StockRecord;
import com.aowin.scm.service.CheckStockService;
import com.aowin.scm.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CheckStockController {
    @Autowired
    private CheckStockService checkStockService;

    @RequestMapping("searchProductStockByMonth")
    public QueryResult searchProductStockByMonth(Integer currentPage, Integer pageSize, DateRange dateRange){
        Page page = new Page(currentPage, pageSize);
        int total = checkStockService.countProductStockByMonth(dateRange);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("startDate", dateRange.getStartDate());
        params.put("endDate", dateRange.getEndDate());
        List<CheckStock> list = checkStockService.searchProductStockByMonth(params);
        return QueryResult.ok(total, list);
    }

}
