package com.test.widget.api_call;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.test.widget.MyApplication;
import com.test.widget.utils.AppConstants;
import com.test.widget.utils.AppLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Spidulica on 11-Nov-16.
 */

public class ApiCalls {

    private static final int NUMBER_CACHE_IMG = 20;

    private static ApiCalls instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private ApiCalls(Context context) {
        requestQueue = Volley.newRequestQueue(context);

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>(NUMBER_CACHE_IMG);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized ApiCalls getInstance(Context context) {
        if (instance == null) {
            instance = new ApiCalls(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void getRequest(final String url, final CallbackApiListener callbackApiListener) {

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppLog.i(this.getClass(), "Url : " + url + response.toString());
                        callbackApiListener.onSuccessfulResponse(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppLog.i(this.getClass(), "Url : " + AppUrlConstants.BASE_URL + "\nError :" + error.toString());
                        callbackApiListener.onFailResponse();
                    }
                }
        );

        getRequest.setRetryPolicy(new DefaultRetryPolicy(2 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        getRequest.setTag(MyApplication.TAG);
        // add it to the RequestQueue
        requestQueue.add(getRequest);
    }

    public void getStringRequest(final String url, final CallbackApiListener callbackApiListener) {

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppLog.i(this.getClass(), "Url : " + url + response.toString());
                        callbackApiListener.onSuccessfulResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppLog.i(this.getClass(), "Url : " + AppUrlConstants.BASE_URL + "\nError :" + error.toString());
                        callbackApiListener.onFailResponse();
                    }
                }
        );

        getRequest.setRetryPolicy(new DefaultRetryPolicy(2 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        getRequest.setTag(MyApplication.TAG);
        // add it to the RequestQueue
        requestQueue.add(getRequest);
    }

    public void postRequestWithArgs(final HashMap<String, String> params, final String url_ending, final CallbackApiListener callbackApiListener) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppUrlConstants.BASE_URL + url_ending,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppLog.i(this.getClass(), "Params : " + params.toString() + "\nUrl : " + AppUrlConstants.BASE_URL + url_ending);
                        callbackApiListener.onSuccessfulResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppLog.i(this.getClass(), "Params : " + params.toString() + "\nUrl : " + AppUrlConstants.BASE_URL + url_ending + "\nError :" + error.toString());
                        callbackApiListener.onFailResponse();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(AppConstants.MINUTE,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        postRequest.setTag(MyApplication.TAG);
        // add it to the RequestQueue
        requestQueue.add(postRequest);
    }

    public void postRequestWithBody(final String body, final String url_ending, final CallbackApiListener onCallbackApiListener) {
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, AppUrlConstants.BASE_URL + url_ending, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppLog.i(this.getClass(), "Body : " + body + "\nUrl : " + AppUrlConstants.BASE_URL + url_ending);
                        onCallbackApiListener.onSuccessfulResponse(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppLog.i(this.getClass(), "Body : " + body + "\nUrl : " + AppUrlConstants.BASE_URL + url_ending + "\nError :" + error.toString());
                        onCallbackApiListener.onFailResponse();
                    }
                });

        RetryPolicy policy = new DefaultRetryPolicy(AppConstants.MINUTE,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        postRequest.setTag(MyApplication.TAG);
        requestQueue.add(postRequest);
    }

}
