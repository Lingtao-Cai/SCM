package com.aowin.scm.service;

import com.aowin.scm.pojo.Vender;

import java.util.List;
import java.util.Map;

public interface VenderService {
    List<Vender> findAll(Map<String, Object> params);

    int countAll();

    void addVender(Vender vender);

    void removeVender(String venderCode);

    Vender findVenderByCode(String venderCode);

    void updateVender(Vender vender);

    List<Vender> searchVender(Map<String, Object> params);

    int countAllResults(Map<String, Object> params);

    List<Vender> findAllVender();
}
