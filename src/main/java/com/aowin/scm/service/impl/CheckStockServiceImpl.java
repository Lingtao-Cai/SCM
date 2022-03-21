package com.aowin.scm.service.impl;

import com.aowin.scm.dao.CheckStockDao;
import com.aowin.scm.pojo.CheckStock;
import com.aowin.scm.pojo.DateRange;
import com.aowin.scm.service.CheckStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CheckStockServiceImpl implements CheckStockService {
    @Autowired
    private CheckStockDao checkStockDao;

    @Override
    public List<CheckStock> searchProductStockByMonth(Map<String, Object> params) {
        return checkStockDao.searchProductStockByMonth(params);
    }

    @Override
    public int countProductStockByMonth(DateRange dateRange) {
        return checkStockDao.countProductStockByMonth(dateRange);
    }
}
