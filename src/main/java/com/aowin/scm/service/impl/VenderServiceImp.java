package com.aowin.scm.service.impl;

import com.aowin.scm.dao.VenderDao;
import com.aowin.scm.pojo.Vender;
import com.aowin.scm.service.VenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VenderServiceImp implements VenderService {

    @Autowired
    private VenderDao venderDao;

    @Override
    public List<Vender> findAll(Map<String, Object> params) {
        return venderDao.findAll(params);
    }

    @Override
    public int countAll() {
        return venderDao.countAll();
    }

    @Override
    public void addVender(Vender vender) {
        venderDao.addVender(vender);
    }

    @Override
    public void removeVender(String venderCode) {
        venderDao.removeVender(venderCode);
    }

    @Override
    public Vender findVenderByCode(String venderCode) {
        return venderDao.findVenderByCode(venderCode);
    }

    @Override
    public void updateVender(Vender vender) {
        venderDao.updateVender(vender);
    }

    @Override
    public List<Vender> searchVender(Map<String, Object> params) {
        return venderDao.searchVender(params);
    }

    @Override
    public int countAllResults(Map<String, Object> params) {
        return venderDao.countAllResults(params);
    }

    @Override
    public List<Vender> findAllVender() {
        return venderDao.findAllVender();
    }
}
