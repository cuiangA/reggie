package com.ca.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ca.reggie.pojo.Employee;
import com.ca.reggie.service.EmployeeService;
import com.ca.reggie.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author 1
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2023-01-09 21:24:19
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

}




