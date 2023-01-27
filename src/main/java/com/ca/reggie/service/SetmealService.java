package com.ca.reggie.service;

import com.ca.reggie.dto.SetmealDto;
import com.ca.reggie.pojo.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 1
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2023-01-12 19:27:04
*/
public interface SetmealService extends IService<Setmeal> {
    void addSetmeal(SetmealDto setmealDto);
    void updateWithSetmeal(SetmealDto setmealDto);
}
