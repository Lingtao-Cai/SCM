package com.aowin.scm.service;

import com.aowin.scm.pojo.DateRange;
import com.aowin.scm.pojo.PayRecord;
import com.aowin.scm.pojo.QueryCondition;

import java.util.List;
import java.util.Map;

public interface PayRecordService {
    void addPayRecord(PayRecord payRecord);

    List<PayRecord> findPayRecords(Map<String, Object> params);

    int countAllPayRecords(QueryCondition queryCondition);

    List<PayRecord> searchPayRecordByMonth(Map<String, Object> params);

    int countPayRecordByMonth(DateRange dateRange);
}
