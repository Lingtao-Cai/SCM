package com.aowin.scm.service;

import com.aowin.scm.pojo.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SoMainService {
    List<SoMain> findAll(Map<String, Object> params);

    int countAll(String account);

    SoMain findBySoId(BigDecimal soId);

    void addSoMain(SoMain soMain);

    void updateSoMain(SoMain soMain);

    void removeSoMain(BigDecimal soId);

    List<SoMain> findBySoPayType(SoMain soMain);

    void endSoMain(SoMain soMain);

    List<SoMain> queryByConditions(Map<String, Object> params);

    int countAllByCondition(QueryCondition queryCondition);

    List<SoMain> findStockOutByPayType(SoMain soMain);

    void stockSoMain(SoMain soMain);

    List<SoMain> findIncomeSoMain(Map<String, Object> params);

    int countIncomeSoMain(SoMain soMain);

    void receiveMoney(SoMain soMain);

    SoMainResult reportSoMainDetails(DateRange dateRange);


}
