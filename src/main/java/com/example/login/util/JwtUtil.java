package com.example.login.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    /**
     * 登录成功：生成 token
     */
    public String createToken(String userId, String username) {
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(userId)                    // token 里存 userId
                .claim("username", username)        // 额外存 username
                .issuedAt(now)                      // 签发时间
                .expiration(expireAt)               // 过期时间
                .signWith(secretKey)                // 用密钥签名
                .compact();                         // 变成字符串
    }

    /**
     * 拦截器：解析并验证 token
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}