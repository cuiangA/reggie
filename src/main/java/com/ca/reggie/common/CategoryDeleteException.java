package com.ca.reggie.common;

import org.apache.logging.log4j.message.Message;

/**
 * 删除菜品或套餐类型时有关联菜品，套餐
 */
public class CategoryDeleteException extends RuntimeException{
    public CategoryDeleteException(String message){
        super(message);
    }
}
