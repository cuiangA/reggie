package com.ca.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ca.reggie.common.R;
import com.ca.reggie.dto.DishDto;
import com.ca.reggie.pojo.Category;
import com.ca.reggie.pojo.Dish;
import com.ca.reggie.pojo.DishFlavor;
import com.ca.reggie.service.CategoryService;
import com.ca.reggie.service.DishFlavorService;
import com.ca.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> add(@RequestBody DishDto dish){
        dishService.saveDish(dish);
        return R.success("添加成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){

        Page<Dish> dishPage = new Page(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name)
                .orderByDesc(Dish::getUpdateTime);
        dishService.page(dishPage,queryWrapper);

        BeanUtils.copyProperties(dishPage,dishDtoPage,"records");

        List<Dish> records = dishPage.getRecords();
        List<DishDto> dishDtoList = records.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).toList();
        dishDtoPage.setRecords(dishDtoList);
        return R.success(dishDtoPage);
    }
    @GetMapping("/{DishID}")
    public R<DishDto> getTheDish(@PathVariable Long DishID){
        Dish dish = dishService.getById(DishID);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        Long id = dishDto.getId();
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> dishFlavorList = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(dishFlavorList);
        return R.success(dishDto);
    }
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateDish(dishDto);
        return R.success("成功更改");
    }
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable int status,@RequestParam List<Long> ids){
        List<Dish> dishes = dishService.listByIds(ids);
        List<Dish> dishes_ = dishes.stream().map(item -> {
            item.setStatus(status);
            return item;
        }).toList();
        dishService.updateBatchById(dishes_);
        return R.success("成功更新");
    }
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        dishService.removeBatchByIds(ids);
        return R.success("删除成功");
    }
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,dish.getCategoryId())
                .eq(StringUtils.isNotEmpty(dish.getName()),Dish::getName,dish.getName())
                .orderByDesc(Dish::getUpdateTime);
        List<Dish> dishList = dishService.list(queryWrapper);
        List<DishDto> dishDtos = dishList.stream().map(dish1 -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish1, dishDto);
            LambdaQueryWrapper<DishFlavor> queryWrapperF = new LambdaQueryWrapper<>();
            queryWrapperF.eq(DishFlavor::getDishId, dish1.getId());
            dishDto.setFlavors(dishFlavorService.list(queryWrapperF));
            return dishDto;
        }).toList();
        return R.success(dishDtos);
    }
}
