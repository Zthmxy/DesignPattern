package com.hx.main;

import com.hx.service.ApiRequest;

public interface ApiAuthencator {
    void auth(String url);
    void auth(ApiRequest apiRequest);
}
