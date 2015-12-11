package com.zjt.comingweather.util;

/**
 * Created by Administrator on 2015/12/11.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
