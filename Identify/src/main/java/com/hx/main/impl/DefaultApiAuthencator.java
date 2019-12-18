package com.hx.main.impl;

import com.hx.main.ApiAuthencator;
import com.hx.service.ApiRequest;
import com.hx.service.AuthToken;
import com.hx.service.CredentialStorage;
import com.hx.service.impl.MysqlCredentialStorage;

public class DefaultApiAuthencator implements ApiAuthencator {

    private CredentialStorage credentialStorage;

    public DefaultApiAuthencator() {
        // 这里可以指定多个存储
        // this.credentialStorage = new MysqlCredentialStorage();
        // Redis, Hbase, Hive, ...
        this.credentialStorage = new MysqlCredentialStorage();
    }
    public DefaultApiAuthencator(CredentialStorage credentialStorage){
        this.credentialStorage = credentialStorage;
    }

    @Override
    public void auth(String url) {
        ApiRequest apiRequest = ApiRequest.createFromFullUrl(url);
        auth(apiRequest);
    }

    @Override
    public void auth(ApiRequest apiRequest) {

        // 获取传入过来的信息
        String userId = apiRequest.getUserID();
        String token = apiRequest.getToken();
        long timestamp = apiRequest.getTimestamp();
        String baseUrl = apiRequest.getBaseUrl();

        // 生成客户端的 Token
        AuthToken clientAuthToken = new AuthToken(token, timestamp);

        if( clientAuthToken.isExpired()) {
            throw new RuntimeException("Token is expired");
        }

        // 根据当前时间 和 用户 id 生成新的 token

        // 获取用户密码
        String password = credentialStorage.getPasswordByUserId(userId);
        AuthToken serverAuthToken = AuthToken.create(baseUrl, timestamp, userId, password);


        if( !clientAuthToken.match(serverAuthToken) ) {
             throw new RuntimeException("Token verfication failed");
        }

    }
}
