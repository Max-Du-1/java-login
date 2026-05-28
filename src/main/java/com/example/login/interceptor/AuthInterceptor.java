package com.example.login.interceptor;

import com.example.login.common.Result;
import com.example.login.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public AuthInterceptor(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 读 Header，名字和你们前端一致：access-token
        String token = request.getHeader("access-token");

        if (token == null || token.isBlank()) {
            write401(response, "请先登录");
            return false;
        }

        try {
            jwtUtil.parseToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            write401(response, "token 已过期，请重新登录");
            return false;
        } catch (JwtException e) {
            write401(response, "token 无效");
            return false;
        }
    }

    private void write401(HttpServletResponse response, String msg) throws Exception {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                objectMapper.writeValueAsString(Result.error(401, msg))
        );
    }
}