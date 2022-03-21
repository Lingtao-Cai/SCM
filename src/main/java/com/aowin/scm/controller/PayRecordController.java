package com.aowin.scm.controller;

import com.aowin.scm.pojo.*;
import com.aowin.scm.service.PayRecordService;
import com.aowin.scm.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PayRecordController {
    @Autowired
    private PayRecordService payRecordService;

    @RequestMapping("queryBill")
    public Result queryBull(QueryCondition queryCondition){
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", queryCondition.getStartDate());
        params.put("endDate", queryCondition.getEndDate());
        params.put("pay_Type", queryCondition.getPay_Type());
        params.put("orderCode", queryCondition.getOrderCode());
        List<PayRecord> list = payRecordService.findPayRecords(params);
        return Result.ok(list);
    }

    @RequestMapping("searchPayRecordByMonth")
    public QueryResult searchPayRecordByMonth(Integer currentPage, Integer pageSize, DateRange dateRange){
        Page page = new Page(currentPage, pageSize);
        int total = payRecordService.countPayRecordByMonth(dateRange);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("startDate", dateRange.getStartDate());
        params.put("endDate", dateRange.getEndDate());
        List<PayRecord> list = payRecordService.searchPayRecordByMonth(params);
        return QueryResult.ok(total, list);
    }
}
