package com.ca.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ca.reggie.pojo.AddressBook;
import com.ca.reggie.service.AddressBookService;
import com.ca.reggie.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author 1
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2023-01-22 19:14:24
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}




