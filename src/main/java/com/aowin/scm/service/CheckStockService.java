package com.aowin.scm.service;

import com.aowin.scm.pojo.CheckStock;
import com.aowin.scm.pojo.DateRange;

import java.util.List;
import java.util.Map;

public interface CheckStockService {
    List<CheckStock> searchProductStockByMonth(Map<String, Object> params);
    int countProductStockByMonth(DateRange dateRange);
}
