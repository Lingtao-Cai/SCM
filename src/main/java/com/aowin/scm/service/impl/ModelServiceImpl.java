package com.aowin.scm.service.impl;

import com.aowin.scm.dao.ModelDao;
import com.aowin.scm.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelDao modelDao;

    @Override
    public List<String> findModel(String account) {
        return modelDao.findModel(account);
    }

    @Override
    public int countAll() {
        return modelDao.countAll();
    }

    @Override
    public void addModelAccount(String account, String modelCode) {
        modelDao.addModelAccount(account, modelCode);
    }

    @Override
    public void removeModel(String account) {
        modelDao.removeModel(account);
    }

//    @Override
//    public void updateModel(String account, String oldCode, String modelCode) {
//        modelDao.updateModel(account, oldCode,modelCode);
//    }
}
