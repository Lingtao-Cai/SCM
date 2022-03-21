package com.aowin.scm.dao;

import com.aowin.scm.pojo.DateRange;
import com.aowin.scm.pojo.PayRecord;
import com.aowin.scm.pojo.QueryCondition;
import com.aowin.scm.pojo.StockRecord;

import java.util.List;
import java.util.Map;

public interface PayRecordDao {


    void addPayRecord(PayRecord payRecord);

    List<PayRecord> findPayRecords(Map<String, Object> params);

    int countAllPayRecords(QueryCondition queryCondition);

    List<PayRecord> searchPayRecordByMonth(Map<String, Object> params);

    int countPayRecordByMonth(DateRange dateRange);
}
