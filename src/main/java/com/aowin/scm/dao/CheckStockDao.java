package com.aowin.scm.dao;

import com.aowin.scm.pojo.CheckStock;
import com.aowin.scm.pojo.DateRange;

import java.util.List;
import java.util.Map;

public interface CheckStockDao {
    List<CheckStock> searchProductStockByMonth(Map<String, Object> params);
    int countProductStockByMonth(DateRange dateRange);
}
