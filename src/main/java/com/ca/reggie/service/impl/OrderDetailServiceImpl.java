package com.ca.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ca.reggie.pojo.OrderDetail;
import com.ca.reggie.service.OrderDetailService;
import com.ca.reggie.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author 1
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2023-01-15 21:40:50
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




