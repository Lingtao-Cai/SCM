package com.aowin.scm.service.impl;

import com.aowin.scm.dao.ProductDao;
import com.aowin.scm.dao.SoItemDao;
import com.aowin.scm.pojo.CheckStock;
import com.aowin.scm.pojo.DateRange;
import com.aowin.scm.pojo.Product;
import com.aowin.scm.pojo.ProductCondition;
import com.aowin.scm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> findAll(Map<String, Object> params) {
        return productDao.findAll(params);
    }

    @Override
    public int countAll() {
        return productDao.countAll();
    }

    @Override
    public List<Product> findAllProduct() {
        return productDao.findAllProduct();
    }

    @Override
    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    @Override
    public String getCategoryName(String productCode) {
        return productDao.getCategoryName(productCode);
    }

    @Override
    public Product findByCode(String code) {
        return productDao.findByCode(code);
    }

    @Override
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    @Override
    public void removeProduct(String productCode) {
        productDao.removeProduct(productCode);
    }

    @Override
    public void updatePoNum(Product product) {
        productDao.updatePoNum(product);
    }

    @Override
    public List<Product> findByCondition(Map<String, Object> params) {
        return productDao.findByCondition(params);
    }

    @Override
    public int countByCondition(ProductCondition productCondition) {
        return productDao.countByCondition(productCondition);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveCount(CheckStock checkStock) {
        String productCode = checkStock.getProductCode();
        Product product = productDao.findByCode(productCode);
        int num = product.getNum();
        int changeNum = checkStock.getChangeNum();
        checkStock.setOriginNum(num);
        int realNum = 0;
        if (checkStock.getType().equals("损耗")){
            realNum = num - changeNum;
        }else {
            realNum = num + changeNum;
        }
        product.setNum(realNum);
        checkStock.setRealNum(realNum);
        productDao.updateProduct(product);
        productDao.saveCount(checkStock);
    }


//
//    @Override
//    public List<String> findCategoriesId(String name) {
//        return productDao.findCategoriesId(name);
//    }
}
