package com.ca.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ca.reggie.pojo.Orders;
import com.ca.reggie.service.OrdersService;
import com.ca.reggie.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author 1
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2023-01-26 02:38:22
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




