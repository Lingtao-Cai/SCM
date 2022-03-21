package com.aowin.scm.dao;

import com.aowin.scm.pojo.CheckStock;
import com.aowin.scm.pojo.DateRange;
import com.aowin.scm.pojo.Product;
import com.aowin.scm.pojo.ProductCondition;

import java.util.List;
import java.util.Map;

public interface ProductDao {

    List<Product> findAll(Map<String, Object> params);

    int countAll();

    List<Product> findAllProduct();

    void addProduct(Product product);

    String getCategoryName(String productCode);

    Product findByCode(String code);

    void updateProduct(Product product);

    void removeProduct(String productCode);

    void updatePoNum(Product product);

    void updateSoNum(Product product);

    List<Product> findByCondition(Map<String, Object> params);

    int countByCondition(ProductCondition productCondition);

    void saveCount(CheckStock checkStock);


}
