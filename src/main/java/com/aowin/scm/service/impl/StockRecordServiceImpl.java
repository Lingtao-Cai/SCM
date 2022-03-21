package com.aowin.scm.service.impl;

import com.aowin.scm.dao.StockRecordDao;
import com.aowin.scm.pojo.DateRange;
import com.aowin.scm.pojo.StockRecord;
import com.aowin.scm.service.StockRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockRecordServiceImpl implements StockRecordService {
    @Autowired
    private StockRecordDao stockRecordDao;

    @Override
    public void addStockRecord(StockRecord stockRecord) {
        stockRecordDao.addStockRecord(stockRecord);
    }

    @Override
    public List<StockRecord> findStockRecords(Map<String, Object> params) {
        return stockRecordDao.findStockRecords(params);
    }

    @Override
    public int countAllRecords(String productCode) {
        return stockRecordDao.countAllRecords(productCode);
    }

    @Override
    public List<StockRecord> searchStockInByMonth(Map<String, Object> params) {
        return stockRecordDao.searchStockInByMonth(params);
    }

    @Override
    public int countStockInByMonth(DateRange dateRange) {
        return stockRecordDao.countStockInByMonth(dateRange);
    }

    @Override
    public List<StockRecord> searchStockOutByMonth(Map<String, Object> params) {
        return stockRecordDao.searchStockOutByMonth(params);
    }

    @Override
    public int countStockOutByMonth(DateRange dateRange) {
        return stockRecordDao.countStockOutByMonth(dateRange);
    }
}
