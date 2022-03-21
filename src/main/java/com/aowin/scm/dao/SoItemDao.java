package com.aowin.scm.dao;

import com.aowin.scm.pojo.PoItem;
import com.aowin.scm.pojo.SoItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SoItemDao {
    SoItem getSoItemByProCode(String productCode);

    List<SoItem> findAll(Map<String, Object> params);

    int countAll(String soId);

    void addSoItem(SoItem soItem);

    void removeSoItem(BigDecimal soId,String productCode);

    List<SoItem> findBySoId(BigDecimal soId);

    int findNum(SoItem soItem);
}
