package com.ca.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ca.reggie.pojo.ShoppingCart;
import com.ca.reggie.service.ShoppingCartService;
import com.ca.reggie.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author 1
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2023-01-25 10:29:51
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




