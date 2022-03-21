package com.aowin.scm.dao;

import com.aowin.scm.pojo.Category;

import java.util.List;
import java.util.Map;

public interface CategoryDao {

    List<Category> findAll(Map<String, Object> params);

    int countAll();

    void addCategory(Category category);

    void updateCategory(Category category);

    void removeCategory(Integer categoryId);

    List<String> findCategoryName(Integer id);

    List<Integer> findCategoriesId(String name);

    List<String> getAllCategoryName();
}
