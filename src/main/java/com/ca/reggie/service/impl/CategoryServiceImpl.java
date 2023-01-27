package com.ca.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ca.reggie.common.CategoryDeleteException;
import com.ca.reggie.mapper.DishMapper;
import com.ca.reggie.mapper.SetmealMapper;
import com.ca.reggie.pojo.Category;
import com.ca.reggie.pojo.Dish;
import com.ca.reggie.pojo.Setmeal;
import com.ca.reggie.service.CategoryService;
import com.ca.reggie.mapper.CategoryMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 1
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2023-01-12 18:12:52
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Resource
    private DishMapper dishMapper;
    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Override
    public void removeCategory(Long id) {
        LambdaQueryWrapper<Dish> queryWrapperD = new LambdaQueryWrapper<>();
        queryWrapperD.eq(Dish::getCategoryId,id);
        Long countDish = dishMapper.selectCount(queryWrapperD);
        LambdaQueryWrapper<Setmeal> queryWrapperS = new LambdaQueryWrapper<>();
        if (countDish>0){
            throw new CategoryDeleteException("该类型存在关联菜品");
        }
        queryWrapperS.eq(Setmeal::getCategoryId,id);
        Long countSetmeal = setmealMapper.selectCount(queryWrapperS);
        if (countSetmeal>0){
            throw new CategoryDeleteException("该类型存在关联套餐");
        }
        categoryMapper.deleteById(id);
    }
}




