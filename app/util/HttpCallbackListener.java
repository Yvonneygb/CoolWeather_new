package com.coolweather.app.util;

/**
 * Created by LX-PC on 2017/5/26.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
