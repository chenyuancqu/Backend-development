package com.example.demo.service;

import com.example.demo.Domain.ApiRequest;
import com.example.demo.Domain.AuthToken;
import com.example.demo.Domain.CredentialStorage;
import com.example.demo.Domain.MysqlCredentialStorage;

/**
 * @author: chenyuan
 * @date: 2025/10/17
 * @description:
 */

public class DefaultApiAuthenticator implements ApiAuthenticator {
    private CredentialStorage credentialStorage;

    public DefaultApiAuthenticator() {
        this.credentialStorage = new MysqlCredentialStorage();
    }

    @Override
    public void auth(String url) {
        ApiRequest apiRequest = ApiRequest.buildFromUrl(url);
        auth(apiRequest);
    }

    @Override
    public void auth(ApiRequest apiRequest) {
        String appId = apiRequest.getAppId();
        String token = apiRequest.getToken();
        long timestamp = apiRequest.getTimestamp();
        String baseUrl = apiRequest.getBaseUrl();

        AuthToken clientAuthToken = new AuthToken(token, timestamp);
        if (clientAuthToken.isExpired()) {
            throw new RuntimeException("Token is expired.");
        }

        String password = credentialStorage.getPasswordByAppId(appId);
        if (password == null) {
            throw new RuntimeException("无效的 AppId");
        }

        AuthToken serverAuthToken = AuthToken.create(baseUrl, appId, password, timestamp);
        if (!serverAuthToken.match(clientAuthToken)) {
            throw new RuntimeException("Token verification failed.");
        }

        System.out.println("✅ 鉴权通过！");
    }
}
