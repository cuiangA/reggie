package com.ca.reggie.dto;

import com.ca.reggie.pojo.User;
import lombok.Data;

@Data
public class UserDto extends User {
    private String code;
}
