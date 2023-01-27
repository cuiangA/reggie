package com.ca.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ca.reggie.pojo.DishFlavor;
import com.ca.reggie.service.DishFlavorService;
import com.ca.reggie.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author 1
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2023-01-13 23:28:42
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




