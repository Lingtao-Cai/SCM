package com.aowin.scm.service.impl;

import com.aowin.scm.dao.PoItemDao;
import com.aowin.scm.dao.SoItemDao;
import com.aowin.scm.pojo.PoItem;
import com.aowin.scm.pojo.SoItem;
import com.aowin.scm.service.SoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SoItemServiceImpl implements SoItemService {

    @Autowired
    private SoItemDao soItemDao;

    @Override
    public SoItem getSoItemByProCode(String productCode) {
        return soItemDao.getSoItemByProCode(productCode);
    }

    @Override
    public List<SoItem> findAll(Map<String, Object> params) {
        return soItemDao.findAll(params);
    }

    @Override
    public int countAll(String soId) {
        return soItemDao.countAll(soId);
    }
}
