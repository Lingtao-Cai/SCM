package com.aowin.scm.service;

import java.util.List;

public interface ModelService {

    List<String> findModel(String account);

    int countAll();

    void addModelAccount(String account, String modelCode);

    void removeModel(String account);

//    void updateModel(String account, String oldCode,String modelCode);
}
