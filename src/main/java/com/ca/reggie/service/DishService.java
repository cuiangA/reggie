package com.ca.reggie.service;

import com.ca.reggie.dto.DishDto;
import com.ca.reggie.pojo.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 1
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2023-01-12 19:26:55
*/
public interface DishService extends IService<Dish> {
        void saveDish(DishDto dishDto);
        void updateDish(DishDto dishDto);
}
