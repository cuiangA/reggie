package com.ca.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ca.reggie.common.R;
import com.ca.reggie.dto.UserDto;
import com.ca.reggie.pojo.User;
import com.ca.reggie.service.UserService;
import com.ca.reggie.utils.ValidateCodeUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)){
            Integer code = ValidateCodeUtils.generateValidateCode(6);
            log.info(code.toString());
            session.setAttribute("code",code);
        }
        return R.success("success");
    }
    @PostMapping("/login")
    public R<String> login(@RequestBody UserDto user, HttpSession session){
        String phone = user.getPhone();
        long phoneLong = Long.parseLong(phone);
        String code = session.getAttribute("code").toString();
        if (user.getCode().equals(code)){

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user1 = userService.getOne(queryWrapper);
            if (user1==null){
                User newUser = new User();
                newUser.setPhone(phone);
                userService.save(newUser);
                session.setAttribute("id",newUser.getId());
            }else {
                session.setAttribute("id",user1.getId());
            }
            return R.success("success");
        }
        return R.success("error");
    }
}
