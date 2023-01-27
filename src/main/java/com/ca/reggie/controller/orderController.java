package com.ca.reggie.controller;

import com.ca.reggie.common.R;
import com.ca.reggie.pojo.Orders;
import com.ca.reggie.service.OrdersService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/order")
public class orderController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders, HttpSession session){
        Object userID = session.getAttribute("id");


        return R.success("success");
    }
}
