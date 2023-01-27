package com.ca.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ca.reggie.common.R;
import com.ca.reggie.pojo.ShoppingCart;
import com.ca.reggie.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class shoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        List<ShoppingCart> list = shoppingCartService.list();
        return R.success(list);
    }
//    amount:168 dishFlavor:"热饮" dishId:"1397851668262465537" image:"0f4bd884-dc9c-4cf9-b59e-7d5958fec3dd.jpg" name:"口味蛇"
    @PostMapping("/add")
    public R<String> add(@RequestBody ShoppingCart shoppingCart, HttpSession session){
        //根据userID，口味，以及对应的菜品或套餐id来查询，如果存在值，则将其数量加一，否则新建一个
        Long userID = (Long)session.getAttribute("id");
        shoppingCart.setUserId(userID);
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userID)
                        .eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId())
                        .eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId())
                        .eq(StringUtils.isNotEmpty(shoppingCart.getDishFlavor()),ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor());
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);
        if (one!=null){
            one.setNumber(one.getNumber()+1);
            shoppingCartService.updateById(one);
        }else {
            shoppingCartService.save(shoppingCart);
        }
        return R.success("success");
    }
    @DeleteMapping("/clean")
    public R<String> clean(HttpSession session){
        Long userID = (Long)session.getAttribute("id");
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userID);
        shoppingCartService.remove(queryWrapper);
        return R.success("clean");
    }
    @PostMapping("/sub")
    public R<String> subtract(@RequestBody ShoppingCart shoppingCart,HttpSession session){
        Long userID = (Long)session.getAttribute("id");
        shoppingCart.setUserId(userID);
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userID)
                .eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId())
                .eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId())
                .eq(StringUtils.isNotEmpty(shoppingCart.getDishFlavor()),ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor());
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);
        if (one.getNumber()>1){
            one.setNumber(one.getNumber()-1);
            shoppingCartService.updateById(one);
        }else {
            shoppingCartService.removeById(one);
        }
        return R.success("subtract");
    }
}
