package com.test.widget.api_call;

import org.json.JSONObject;

/**
 * Created by Alexandra on 16/04/2015.
 */
public interface CallbackApiListener {
    void onSuccessfulResponse(String response);
    void onFailResponse();
}
