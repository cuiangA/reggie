package com.ca.reggie;

import com.ca.reggie.mapper.EmployeeMapper;
import com.ca.reggie.pojo.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ReggieApplicationTests {
    @Autowired
    EmployeeMapper employeeMapper;
    @Test
    void contextLoads() {
        Employee employee = new Employee();
        employee.setName("123");
        employee.setSex("1");
        employee.setUsername("12345");
        employee.setPhone("12312341234");
        employee.setIdNumber("111222333444555666");
        employee.setPassword("123456");
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(1L);
        employee.setUpdateUser(1L);
        int insert = employeeMapper.insert(employee);
    }

}
