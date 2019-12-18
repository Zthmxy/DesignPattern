package com.hx.service;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Map;

public class AuthToken {

    private long createTime; // 令牌创建时间
    private static final long DEFAULT_EXPIRED_TIME_INTERVAL = 1 * 60 * 1000; // 1 Min
    private String token; // 令牌
    private long expiredTimeInterval; // 过期时间

    //构造函数
    public AuthToken(String token, long createTime) {

        this(token, createTime, DEFAULT_EXPIRED_TIME_INTERVAL);
    }

    public AuthToken(String token, long createTime, long expiredTimeInterval) {
        this.token = token;
        this.createTime = createTime;
        this.expiredTimeInterval = expiredTimeInterval;
    }

    // 将 url , userID , password, timestamp 拼接成一个字符串
    // 将字符串加密成 Token
    // web格式: www.baidu.com?userID=123&timestamp=xxx&token=123456x
    public static AuthToken create(String baseUrl, long createTime, String userId, String password) {


        String url = baseUrl +
                "?"+"userID="+ userId +
                "&"+"timestamp=" + createTime +
                "&"+"password=" + password;


        //TODO 生成TOKEN
        String token = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(url.getBytes());
            byte[] digest = md5.digest();
            token = new String(digest);
        }catch (Exception e) {
            token = "123456";
        }


        return new AuthToken(token, createTime);
    }


    // 获取Token

    public String getToken() {
        return this.token;
    }


    // 判断 Token 时间是否过期

    public boolean isExpired() {
        // 令牌创建之间 + 过期时间 > 当前时间 就是 过期了 Token 无效了
        return createTime + expiredTimeInterval < System.currentTimeMillis() ? true : false;
    }

    // 验证两个 Token 是否匹配
    public boolean match(AuthToken authToken) {
        return this.token.equals(authToken.token);
    }


}
