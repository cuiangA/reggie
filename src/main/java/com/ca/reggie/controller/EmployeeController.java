package com.ca.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ca.reggie.common.R;
import com.ca.reggie.pojo.Employee;
import com.ca.reggie.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;

import java.time.LocalDateTime;
import java.util.Date;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request){
        //1.对密码进行MD5加密
        String password = employee.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        //2.通过用户名查询数据
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.like(Employee::getUsername,employee.getUsername());
        Employee employee1 = employeeService.getOne(employeeLambdaQueryWrapper);
        //检验账户是否存在
        if (employee1==null){
            return R.error("用户名不存在");
        }
        //检验密码是否正确
        if (!employee1.getPassword().equals(password)){
            return R.error("密码错误");
        }
        //检验账户状态
        if (employee1.getStatus()==0){
            return R.error("账户已禁用");
        }
        //将员工id放入session
        request.getSession().setAttribute("employee",employee1.getId());

        return R.success(employee1);
    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    @PostMapping
    public R<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee){
        String defaultPassword = "123456";
        defaultPassword= DigestUtils.md5DigestAsHex(defaultPassword.getBytes());
        employee.setPassword(defaultPassword);
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        Long id = (Long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(id);
//        employee.setUpdateUser(id);
        employeeService.save(employee);
        return R.success("创建成功");
    }
    @GetMapping("/page")
    public R<Page> page(String name,int pageSize,int page){
        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name)
                .orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,employeeLambdaQueryWrapper);
        return R.success(pageInfo);
    }
    @PutMapping
    public R<String> update(@RequestBody Employee employee){
        employeeService.updateById(employee);
        return R.success("更新成功");
    }
    @GetMapping("/{id}")
    public R<Employee> getEmployeeByID(@PathVariable Long id){
        Employee emp = employeeService.getById(id);
        if (emp!=null){
            return R.success(emp);
        }
        return R.error("获取信息失败");

    }
}
