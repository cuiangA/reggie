package com.ca.reggie.service;

import com.ca.reggie.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 1
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2023-01-12 18:12:52
*/
public interface CategoryService extends IService<Category> {
    public void removeCategory(Long id);
}
