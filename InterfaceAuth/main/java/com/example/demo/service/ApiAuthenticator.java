package com.example.demo.service;

import com.example.demo.Domain.ApiRequest;

/**
 * @author: henchman
 * @date: 2025/10/17
 * @description: 接口类，暴露API接口
 */
public interface ApiAuthenticator {
    void auth(String url);
    void auth(ApiRequest apiRequest);
}
