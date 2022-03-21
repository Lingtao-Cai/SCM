package com.aowin.scm.service;

import com.aowin.scm.pojo.PoItem;
import com.aowin.scm.pojo.SoItem;

import java.util.List;
import java.util.Map;

public interface SoItemService {
    SoItem getSoItemByProCode(String productCode);

    List<SoItem> findAll(Map<String, Object> params);

    int countAll(String poId);
}
