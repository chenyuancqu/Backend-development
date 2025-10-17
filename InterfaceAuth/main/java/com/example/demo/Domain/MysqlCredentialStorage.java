package com.example.demo.Domain;

/**
 * @author: chenyuan
 * @date: 2025/10/17
 * @description:
 */
public class MysqlCredentialStorage implements CredentialStorage {

    // 模拟查询数据库，此处直接返回
    @Override
    public String getPasswordByAppId(String appId) {
        return "password123";
    }
}
