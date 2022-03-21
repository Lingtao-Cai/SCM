package com.aowin.scm.controller;

import com.aowin.scm.pojo.Category;
import com.aowin.scm.pojo.QueryResult;
import com.aowin.scm.pojo.Result;
import com.aowin.scm.pojo.ScmUser;
import com.aowin.scm.service.CategoryService;
import com.aowin.scm.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("categories")
    public QueryResult getAll(Integer currentPage, Integer pageSize, HttpServletRequest request) {
        Page page = new Page(currentPage, pageSize);
        int total = categoryService.countAll();
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        List<Category> list = categoryService.findAll(params);
        return QueryResult.ok(total, list);
    }


    @PostMapping("addCategory")
    public Result addCategory(Category category){
        categoryService.addCategory(category);
        return Result.ok(null, "添加成功");
    }

    @PostMapping("updateCategory")
    public Result updateCategory(Category category){
        //System.out.println("category" + category);
       // System.out.println("id" + categoryId);
        categoryService.updateCategory(category);
        return Result.ok(null, "修改成功");
    }

    @PostMapping("removeCategory")
    public Result removeCategory(Integer categoryId){
        categoryService.removeCategory(categoryId);
        return Result.ok(null, "删除成功");
    }

    @PostMapping("getAllCategoryName")
    public Result getAllCategoryName(){
        return Result.ok(categoryService.getAllCategoryName());
    }

    @PostMapping("getCategoryId")
    public Result getCategoryId(String categoryName){
        return Result.ok(categoryService.findCategoriesId(categoryName).get(0));
    }
}
