package com.ca.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ca.reggie.common.R;
import com.ca.reggie.dto.SetmealDto;
import com.ca.reggie.pojo.*;
import com.ca.reggie.service.CategoryService;
import com.ca.reggie.service.SetmealDishService;
import com.ca.reggie.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        Page<SetmealDto> thePage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(name),Setmeal::getName,name)
                .orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(setmealPage,queryWrapper);
        BeanUtils.copyProperties(setmealPage,thePage,"records");
        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealDto> setmealDtos = records.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Category category = categoryService.getById(item.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).toList();
        thePage.setRecords(setmealDtos);
        return R.success(thePage);
    }
    @PostMapping
    public R<String> addSetmeal(@RequestBody SetmealDto setmealDto){
        setmealService.addSetmeal(setmealDto);
        return R.success("success");
    }
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable int status,@RequestParam List<Long> ids){
        List<Setmeal> setmeals = setmealService.listByIds(ids);
        List<Setmeal> setmeals_ = setmeals.stream().map(item -> {
            item.setStatus(status);
            return item;
        }).toList();
        setmealService.updateBatchById(setmeals_);
        return R.success("成功更新");
    }
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeBatchByIds(ids);
        return R.success("删除成功");
    }
    @GetMapping("/{ids}")
    public R<SetmealDto> getTheSetmeal(@PathVariable Long ids){
        Setmeal setmeal = setmealService.getById(ids);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,ids);
        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(setmealDishes);
        return R.success(setmealDto);
    }
    @PutMapping
    public R<String> updateSetmeal(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithSetmeal(setmealDto);
        return R.success("success");
    }
    @GetMapping("/list")
    public R<List<Setmeal>> list(String categoryId,int status){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId,Long.parseLong(categoryId))
                .eq(Setmeal::getStatus,status);
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
