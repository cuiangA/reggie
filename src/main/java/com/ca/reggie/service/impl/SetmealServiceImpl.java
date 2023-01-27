package com.ca.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ca.reggie.dto.SetmealDto;
import com.ca.reggie.pojo.Setmeal;
import com.ca.reggie.pojo.SetmealDish;
import com.ca.reggie.service.SetmealDishService;
import com.ca.reggie.service.SetmealService;
import com.ca.reggie.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 1
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2023-01-12 19:27:04
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{
    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    @Transactional
    public void addSetmeal(SetmealDto setmealDto) {
        this.save(setmealDto);
        Long id = setmealDto.getId();
        List<SetmealDish> list = setSetmealID(setmealDto, id.toString());
        setmealDishService.saveBatch(list);
    }

    @Override
    @Transactional
    public void updateWithSetmeal(SetmealDto setmealDto) {
         this.updateById(setmealDto);
        Long id = setmealDto.getId();
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        setmealDishService.remove(queryWrapper);

        List<SetmealDish> list = setSetmealID(setmealDto, id.toString());
        setmealDishService.saveBatch(list);
    }
    private List<SetmealDish> setSetmealID(SetmealDto setmealDto,String id){
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        List<SetmealDish> list = setmealDishes.stream().map(item -> {
            item.setSetmealId(id);
            return item;
        }).toList();
        return list;
    }
}




