package com.aowin.scm.service;

import com.aowin.scm.pojo.DateRange;
import com.aowin.scm.pojo.StockRecord;

import java.util.List;
import java.util.Map;

public interface StockRecordService {

    void addStockRecord(StockRecord stockRecord);

    List<StockRecord> findStockRecords(Map<String, Object> params);

    int countAllRecords(String productCode);

    List<StockRecord> searchStockInByMonth(Map<String, Object> params);

    int countStockInByMonth(DateRange dateRange);

    List<StockRecord> searchStockOutByMonth(Map<String, Object> params);

    int countStockOutByMonth(DateRange dateRange);
}
