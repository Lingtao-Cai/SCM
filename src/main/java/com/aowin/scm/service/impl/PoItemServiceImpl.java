package com.aowin.scm.service.impl;

import com.aowin.scm.dao.PoItemDao;
import com.aowin.scm.pojo.PoItem;
import com.aowin.scm.service.PoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PoItemServiceImpl implements PoItemService {
    @Autowired
    private PoItemDao poItemDao;

    @Override
    public List<PoItem> findAll(Map<String, Object> params) {
        return poItemDao.findAll(params);
    }

    @Override
    public int countAll(String poId) {
        return poItemDao.countAll(poId);
    }
}
