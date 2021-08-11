package com.nutrili.config.Security.Components;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

 public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    @Override
    protected String obtainUsername(HttpServletRequest request)
    {
        String username = request.getParameter(getUsernameParameter());
        String type = request.getParameter("type");
        String smsCode= request.getParameter("smsCode");
        String combinedUsername = username + ":" + type +  ":" + smsCode;
        return combinedUsername;
    }
}