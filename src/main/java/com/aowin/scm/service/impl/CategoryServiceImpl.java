package com.aowin.scm.service.impl;

import com.aowin.scm.dao.CategoryDao;
import com.aowin.scm.pojo.Category;
import com.aowin.scm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;


    @Override
    public List<Category> findAll(Map<String, Object> params) {
        return categoryDao.findAll(params);
    }

    @Override
    public int countAll() {
        return categoryDao.countAll();
    }

    @Override
    public void addCategory(Category category) {
        categoryDao.addCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    @Override
    public void removeCategory(Integer categoryId) {
        categoryDao.removeCategory(categoryId);
    }

    @Override
    public List<String> findCategoryName(Integer id) {
        return categoryDao.findCategoryName(id);
    }

    @Override
    public List<Integer> findCategoriesId(String name) {
        return categoryDao.findCategoriesId(name);
    }

    @Override
    public List<String> getAllCategoryName() {
        return categoryDao.getAllCategoryName();
    }
}
