package com.ca.reggie.mapper;

import com.ca.reggie.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 1
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2023-01-09 21:24:19
* @Entity com.ca.reggie.domain.Employee
*/
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

}




