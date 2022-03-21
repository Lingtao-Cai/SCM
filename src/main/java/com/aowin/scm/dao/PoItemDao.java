package com.aowin.scm.dao;

import com.aowin.scm.pojo.PoItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PoItemDao {
    List<PoItem> findAll(Map<String, Object> params);

    int countAll(String poId);

    void addPoItem(PoItem poItem);

    void removePoItem(BigDecimal poId);

    List<PoItem> findByPoId(BigDecimal poId);

    int findNum(PoItem poItem);

}
