package com.example.demo.Domain;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: henchman
 * @date: 2025/10/17
 * @description: 负责解析 URL 中的参数（token、appId、timestamp）
 */
public class ApiRequest {
    private String baseUrl;
    private String token;
    private String appId;
    private Long timestamp;

    public ApiRequest() {}

    public static ApiRequest buildFromUrl(String url) {
        ApiRequest apiRequest = new ApiRequest();
        String[] parts = url.split("\\?");
        apiRequest.baseUrl = parts[0];
        Map<String, String> params = parseParams(parts.length > 1 ? parts[1] : "");
        apiRequest.token = params.get("token");
        apiRequest.appId = params.get("appId");
        apiRequest.timestamp = Long.parseLong(params.get("timestamp"));
        return apiRequest;
    }

    private static Map<String, String> parseParams(String paramString) {
        HashMap<String, String> params = new HashMap<>();
        if (paramString == null || paramString.isEmpty()) {return params;}
        String[] pairs = paramString.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                try {
                    params.put(
                            URLDecoder.decode(kv[0], String.valueOf(StandardCharsets.UTF_8)),
                            URLDecoder.decode(kv[1], String.valueOf(StandardCharsets.UTF_8))
                    );
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return params;
    }

    public String getBaseUrl() {return baseUrl;}
    public String getToken() {return token;}
    public String getAppId() {return appId;}
    public Long getTimestamp() {return timestamp;}

}
