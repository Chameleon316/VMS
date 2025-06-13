package org.lw.vms.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.lw.vms.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT (JSON Web Token) 工具类。
 * 用于生成、解析和验证 JWT。
 */
@Component // 标识这是一个 Spring 组件，可以被自动注入
public class JwtUtil {

    // 从 application.properties 中读取 JWT 密钥
    @Value("${jwt.secret}")
    private String secret;

    // 从 application.properties 中读取 JWT 过期时间（毫秒）
    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * 生成 JWT Token。
     *
     * @param user 用户对象，其信息将被放入 Token 的 Claims 中。
     * @return 生成的 JWT 字符串。
     */
    public String generateToken(User user) {
        // 定义 Token 中需要包含的自定义 Claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("username", user.getUsername());
        claims.put("name",user.getName());
        claims.put("contact", user.getContact());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setClaims(claims) // 设置 Claims
                .setSubject(user.getUsername()) // 设置主题 (通常是用户名)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 设置过期时间
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 使用 HS256 算法和密钥签名
                .compact(); // 压缩生成 JWT 字符串
    }

    /**
     * 从 JWT 中提取所有 Claims。
     *
     * @param token JWT 字符串。
     * @return 包含所有 Claims 的对象。
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // 设置用于解析的密钥
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从 Token 中提取特定 Claims。
     *
     * @param token          JWT 字符串。
     * @param claimsResolver 用于从 Claims 中提取特定值的函数。
     * @param <T>            要提取值的类型。
     * @return 提取出的值。
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从 Token 中提取用户名。
     *
     * @param token JWT 字符串。
     * @return 用户名。
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 检查 Token 是否过期。
     *
     * @param token JWT 字符串。
     * @return 如果 Token 已过期则返回 true，否则返回 false。
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 从 Token 中提取过期时间。
     *
     * @param token JWT 字符串。
     * @return 过期时间。
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 获取用于签名的密钥。
     * 密钥从 Base64 编码的秘密字符串中解码。
     *
     * @return 用于签名的密钥。
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 验证 Token 是否有效。
     *
     * @param token    JWT 字符串。
     * @param user 用户对象（用于比对用户名等）。
     * @return 如果 Token 有效且未过期则返回 true，否则返回 false。
     */
    public Boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }
}
