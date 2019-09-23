package com.ego.order.intercepter;

import com.alibaba.fastjson.JSONObject;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@PropertySource(value = "classpath:commons.properties")
public class LoginIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        if(token!=null && !token.equals("")){
            String json = HttpClientUtil.doPost("http://localhost:8082/user/token/" + token);
            EgoResult er = JSONObject.parseObject(json, EgoResult.class);
            if(er.getStatus() == 200){
                return true;
            }
        }

        String num=request.getParameter("num");
        if(num !=null &&!num.equals("")){
            response.sendRedirect("http://localhost:8082/user/showLogin?interurl="+request.getRequestURL()+"?num="+num);
        }else{
            response.sendRedirect("http://localhost:8082/user/showLogin");
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
