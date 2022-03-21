package com.aowin.scm.dao;

import com.aowin.scm.pojo.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PoMainDao {

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

    PoMainResult reportPoMainSum(String startDate, String endDate);

    List<PoMain> reportPoMainDetail(Map<String, Object> params);

    int countPoMainDetail(DateRange dateRange);

    List<PoMain> findAllPoByMonth(DateRange dateRange);

}
