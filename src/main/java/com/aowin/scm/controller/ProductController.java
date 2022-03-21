package com.aowin.scm.controller;

import com.aowin.scm.pojo.*;
import com.aowin.scm.service.ProductService;
import com.aowin.scm.service.SoItemService;
import com.aowin.scm.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private SoItemService soItemService;

    @RequestMapping("products")
    public QueryResult getAll(Integer currentPage, Integer pageSize) {
        Page page = new Page(currentPage, pageSize);
        int total = productService.countAll();
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        List<Product> list = productService.findAll(params);
        return QueryResult.ok(total, list);
    }

    @RequestMapping("findAllProduct")
    public Result findAllProduct(){
        List<Product> list = productService.findAllProduct();
        return Result.ok(list);
    }

    @PostMapping("addProduct")
    public Result addProduct(Product product){
        if (productService.findByCode(product.getProductCode()) != null){
            return Result.error("该产品编号已存在");
        }else {
            productService.addProduct(product);
            return Result.ok(null, "添加成功");
        }
    }

    @PostMapping("getCategoryName")
    public Result getCategoryName(String productCode){
        return Result.ok(productService.getCategoryName(productCode));
    }


    @PostMapping("updateProduct")
    public Result updateProduct(Product product){
        productService.updateProduct(product);
        return Result.ok(null, "修改成功");
    }

    @PostMapping("removeProduct")
    public Result removeProduct(String productCode){
        if (soItemService.getSoItemByProCode(productCode) != null){
            return Result.ok("删除错误:该产品有正在进行的销售单");
        }else {
            productService.removeProduct(productCode);
            return Result.ok(null, "删除成功");
        }
    }

    @RequestMapping("searchStockProduct")
    public QueryResult searchStockProduct(Integer currentPage, Integer pageSize, ProductCondition productCondition){
        Page page = new Page(currentPage, pageSize);
        int total = productService.countByCondition(productCondition);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        params.put("productCode", productCondition.getProductCode());
        params.put("productName", productCondition.getProductName());
        params.put("numMax", productCondition.getNumMax());
        params.put("numMin", productCondition.getNumMin());
        List<Product> list = productService.findByCondition(params);
        QueryResult queryResult = new QueryResult();
        queryResult.setData(list);
        queryResult.setCode(200);
        queryResult.setTotal(total);
        if (list.isEmpty()){
            queryResult.setMsg("查找不到合适的结果");
        }else queryResult.setMsg("查询成功");
        return queryResult;
    }

    @PostMapping("saveCount")
    public Result saveCount(CheckStock checkStock){
        System.out.println("check" + checkStock);
        productService.saveCount(checkStock);
        return Result.ok(null, "盘点成功");
    }

    @RequestMapping("findByProductCode")
    public Result findByProductCode(String productCode){
        Product product = productService.findByCode(productCode);
        return Result.ok(product);
    }

}
