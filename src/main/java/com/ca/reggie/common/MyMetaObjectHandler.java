package com.ca.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        Long id = (Long) requestAttributes.getAttribute("employee", RequestAttributes.SCOPE_SESSION);
        if (id==null){
            id = (Long) requestAttributes.getAttribute("id", RequestAttributes.SCOPE_SESSION);
        }
        if (metaObject.hasSetter("createTime")){
            metaObject.setValue("createTime", LocalDateTime.now());
        }
        if (metaObject.hasSetter("updateTime")){
            metaObject.setValue("updateTime", LocalDateTime.now());
        }
        if (metaObject.hasSetter("createUser")){
            metaObject.setValue("createUser", id);
        }
        if (metaObject.hasSetter("updateUser")){
            metaObject.setValue("updateUser", id);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        Long id = (Long) requestAttributes.getAttribute("id", RequestAttributes.SCOPE_SESSION);
        if (id==null){
            id = (Long) requestAttributes.getAttribute("id", RequestAttributes.SCOPE_SESSION);
        }
        if (metaObject.hasSetter("updateTime")){
            metaObject.setValue("updateTime", LocalDateTime.now());
        }
        if (metaObject.hasSetter("updateUser")){
            metaObject.setValue("updateUser", id);
        }
    }
}
