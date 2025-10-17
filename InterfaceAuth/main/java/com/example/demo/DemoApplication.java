package com.example.demo;

import com.example.demo.Domain.AuthToken;
import com.example.demo.service.ApiAuthenticator;
import com.example.demo.service.DefaultApiAuthenticator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenyuan
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        // 模拟调用方
        String baseUrl = "https://api.example.com/getUser";
        String appId = "app001";
        String password = "password123";
        long timestamp = System.currentTimeMillis();

        AuthToken clientToken = AuthToken.create(baseUrl, appId, password, timestamp);
        String url = baseUrl + "?appId=" + appId + "&timestamp=" + timestamp + "&token=" + clientToken.getToken();

        System.out.println("客户端请求URL: " + url);

        // 模拟服务端鉴权
        ApiAuthenticator authenticator = new DefaultApiAuthenticator();
        authenticator.auth(url);
    }

}
