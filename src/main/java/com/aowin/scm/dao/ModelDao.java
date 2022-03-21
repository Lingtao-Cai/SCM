package com.aowin.scm.dao;

import java.util.List;


public interface ModelDao {

    List<String> findModel(String account);

    int countAll();

    void addModelAccount(String account, String modelCode);

    void removeModel(String account);

//    void updateModel(String account, String oldCode,String modelCode);

}
