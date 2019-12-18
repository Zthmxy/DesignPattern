package com.hx.service.impl;

import com.hx.service.CredentialStorage;

public class MysqlCredentialStorage implements CredentialStorage {
    @Override
    public String getPasswordByUserId(String UserId) {
        return "helloworld";
    }
}
