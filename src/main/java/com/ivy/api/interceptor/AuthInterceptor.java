package com.ivy.api.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

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

        String userAddress = null;
        String signature = null;

        var userAddressCookie = WebUtils.getCookie(request, "ivy-auth-address");
        if (userAddressCookie != null) {
            userAddress = userAddressCookie.getValue();
        }
        var signatureCookie = WebUtils.getCookie(request, "ivy-auth-signature");
        if (signatureCookie != null) {
            signature = signatureCookie.getValue();
        }

        if (userAddress == null) {
            userAddress = request.getHeader("X-Web3-Auth-Address");
        }
        if (signature == null) {
            signature = request.getHeader("X-Web3-Auth-Signature");
        }

        log.debug("attempt login: address {} - signature {}", userAddress, signature);

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
