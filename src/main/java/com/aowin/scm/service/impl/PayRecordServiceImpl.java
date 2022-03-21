package com.aowin.scm.service.impl;

import com.aowin.scm.dao.PayRecordDao;
import com.aowin.scm.pojo.DateRange;
import com.aowin.scm.pojo.PayRecord;
import com.aowin.scm.pojo.QueryCondition;
import com.aowin.scm.service.PayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PayRecordServiceImpl implements PayRecordService {
    @Autowired
    private PayRecordDao payRecordDao;

    @Override
    public void addPayRecord(PayRecord payRecord) {
        payRecordDao.addPayRecord(payRecord);
    }

    @Override
    public List<PayRecord> findPayRecords(Map<String, Object> params) {
        return payRecordDao.findPayRecords(params);
    }

    @Override
    public int countAllPayRecords(QueryCondition queryCondition) {
        return payRecordDao.countAllPayRecords(queryCondition);
    }

    @Override
    public List<PayRecord> searchPayRecordByMonth(Map<String, Object> params) {
        return payRecordDao.searchPayRecordByMonth(params);
    }

    @Override
    public int countPayRecordByMonth(DateRange dateRange) {
        return payRecordDao.countPayRecordByMonth(dateRange);
    }
}
