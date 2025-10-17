package com.example.demo.Domain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author: henchman
 * @date: 2025/10/17
 * @description: 负责 token 生成、校验、过期判断
 */
public class AuthToken {
    private static final long DEFAULT_EXPIRATION_TIME_INTERVAL = 60 * 1000;
    private String token;
    private long createTime;
    private long expirationTimeInterval =  DEFAULT_EXPIRATION_TIME_INTERVAL;

    public AuthToken(String token, long createTime) {
        this.token = token;
        this.createTime = createTime;
    }

    // 从baseUrl、appId、password、timeStamp拼接token
    public static AuthToken create(String baseUrl, String appId, String password, Long timeStamp) {
        String toHash = baseUrl + appId + password + timeStamp;
        String token = sha256(toHash);
        return new AuthToken(token, timeStamp);
    }

    public String getToken() {
        return token;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > this.createTime + this.expirationTimeInterval;
    }

    // 判断token与自己token是否相同
    public boolean match(AuthToken authToken) {
        return this.token.equals(authToken.getToken());
    }

    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Token 生成失败", e);
        }
    }


}
