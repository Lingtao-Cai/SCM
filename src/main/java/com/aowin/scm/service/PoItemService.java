package com.aowin.scm.service;

import com.aowin.scm.pojo.PoItem;

import java.util.List;
import java.util.Map;

public interface PoItemService {

    List<PoItem> findAll(Map<String, Object> params);

    int countAll(String poId);

}
