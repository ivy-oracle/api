package com.ivy.api.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ivy.api.annotation.RequireAuth;
import com.ivy.api.service.AuthService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        RequireAuth requireAuth = null;
        try {
            requireAuth = ((HandlerMethod) handler).getMethod().getAnnotation((RequireAuth.class));
        } catch (ClassCastException e) {

        }
        if (requireAuth == null) {
            return true;
        }

        String userAddress = request.getHeader("X-Web3-Auth-Address");
        String signature = request.getHeader("X-Web3-Auth-Signature");

        Boolean success = authService.authenticate(userAddress, signature);
        if (!success) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print("{\"error\": \"Unauthorized\"}");
            out.flush();

            return false;
        }

        return true;
    }
}
