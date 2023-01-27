package com.ca.reggie.filter;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.reggie.common.R;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

/**
 * 访问权限的过滤（登录
 */
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    static private final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURL = request.getRequestURI();
        String[] freeUrl={
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        Object id = request.getSession().getAttribute("id");
        if (!checkFree(freeUrl,requestURL)&&
                ((request.getSession().getAttribute("employee") == null&&request.getSession().getAttribute("id") == null))){
            response.getOutputStream().write(JSON.toJSONString(R.error("NOTLOGIN")).getBytes());
        }
        filterChain.doFilter(request,response);
    }

    /**
     * 判断请求路径可否直接放行
     * @param free
     * @param requestUrl
     * @return
     */
    public Boolean checkFree(String[] free,String requestUrl){
        for (String url : free) {
            boolean match = PATH_MATCHER.match(url, requestUrl);
            if (match){
                return true;
            }
        }
        return false;
    }
}
