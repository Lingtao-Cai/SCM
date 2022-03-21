package com.aowin.scm.service;

import com.aowin.scm.pojo.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PoMainService {

    List<PoMain> findAll(Map<String, Object> params);

    int countAll(String account);

    PoMain findByPoId(BigDecimal poId);

    void addPoMain(PoMain poMain);

    void updateMain(PoMain poMain);

    void removePoMain(BigDecimal poId);

    List<PoMain> findByPayType(PoMain poMain);

    void endPoMain(PoMain poMain);

    List<PoMain> queryByConditions(Map<String, Object> params);

    int countAllByCondition(QueryCondition queryCondition);

    List<PoMain> findStockPoByPayType(PoMain poMain);

    void stockPoMain(PoMain poMain);

    List<SoMain> findOutcomePoMain(Map<String, Object> params);

    int countOutcomePoMain(PoMain poMain);

    void payMoney(PoMain poMain);

//    List<PoMain> findPoByMonth(Map<String, Object> params);

    PoMainResult reportPoMainSum(String startDate, String endDate);

    List<PoMain> reportPoMainDetail(Map<String, Object> params);

    int countPoMainDetail(DateRange dateRange);

}
