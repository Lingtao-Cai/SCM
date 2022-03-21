package com.aowin.scm.service;

import com.aowin.scm.pojo.CheckStock;
import com.aowin.scm.pojo.DateRange;
import com.aowin.scm.pojo.Product;
import com.aowin.scm.pojo.ProductCondition;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Product> findAll(Map<String, Object> params);

    int countAll();

    List<Product> findAllProduct();

    void addProduct(Product product);

    String getCategoryName(String productCode);

//    List<String> findCategoriesId(String name);

    Product findByCode(String code);

    void updateProduct(Product product);

    void removeProduct(String productCode);

    void updatePoNum(Product product);

    List<Product> findByCondition(Map<String, Object> params);

    int countByCondition(ProductCondition productCondition);

    void saveCount(CheckStock checkStock);

}
