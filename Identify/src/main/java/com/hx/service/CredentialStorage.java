package com.hx.service;

public interface CredentialStorage {

    // 根据 userID 返回密码
    public String getPasswordByUserId(String UserId);
}
