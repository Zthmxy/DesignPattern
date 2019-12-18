package com.hx.service;


// web格式: www.baidu.com?userID=123&timestamp=xxx&token=123456x

public class ApiRequest {

    //  将 Token 放入到 url 中发送过来
    //  解析 url 得到 Token , userID 和 timestamp

    private String baseUrl;
    private String token;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getToken() {
        return token;
    }

    public String getUserID() {
        return userID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    private String userID;
    private long timestamp;

    public ApiRequest(String baseUrl, String token, String userID, long timestamp) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.userID = userID;
        this.timestamp = timestamp;
    }

    public static ApiRequest createFromFullUrl(String url) {

        String[] baseUrlExt = url.split("\\?");

        // 拆分url
        String baseUrl = baseUrlExt[0];
        String ext = baseUrlExt[1];
        String[] listOfStr = ext.split("&");
        // 获取 userID, timeStamp 和 token
        String userID = listOfStr[0].split("=")[1];
        long timestamp = Long.parseLong(listOfStr[1].split("=")[1]);
        String token = listOfStr[2].split("=")[1];
        return new ApiRequest(baseUrl, token, userID, timestamp);
    }
}
