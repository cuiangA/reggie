package com.ca.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ca.reggie.pojo.User;
import com.ca.reggie.service.UserService;
import com.ca.reggie.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 1
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2023-01-17 22:23:51
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




