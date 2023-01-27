package com.ca.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ca.reggie.dto.DishDto;
import com.ca.reggie.pojo.Dish;
import com.ca.reggie.pojo.DishFlavor;
import com.ca.reggie.service.DishFlavorService;
import com.ca.reggie.service.DishService;
import com.ca.reggie.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 1
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2023-01-12 19:26:55
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{
    @Autowired
    private DishFlavorService dishFlavorService;
    @Override
    @Transactional//设计多张表，开启事务控制
    public void saveDish(DishDto dishDto) {
        this.save(dishDto);
        List<DishFlavor> dishFlavors = dishFlavorSetDishID(dishDto);
        dishFlavorService.saveBatch(dishFlavors);
    }

    @Override
    @Transactional
    public void updateDish(DishDto dishDto) {
        //更改dish表
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        List<DishFlavor> dishFlavors = dishFlavorSetDishID(dishDto);
        dishFlavorService.saveBatch(dishFlavors);

    }

    private List<DishFlavor> dishFlavorSetDishID(DishDto dishDto) {
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        List<DishFlavor> flavorList = flavors.stream().map(item -> {
            item.setDishId(id);
            return item;
        }).toList();
        return flavorList;
    }
}




