package com.youyou.zllibrary.httputil;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.commonlibrary.widget.GlobalStore.StoreConfig;
import com.example.commonlibrary.widget.bean.TokenBean;
import com.youyou.zllibrary.util.SpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 张磊 on 2016/5/16.
 * 介绍：
 */
public class HttpUtilNewOne {
    private static String className = HttpUtilNewOne.class.getName();
    private static RequestQueue requestQueue;
    private static SpUtil spUtil;

    public static void init(Context context) {
        spUtil = SpUtil.getInstance(context);
        requestQueue = Volley.newRequestQueue(context);
    }

    public static void get(String url, boolean needToken, boolean justOneTime, Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        StringRequest request;
        if (needToken) {
            try {
                final String token = getToken();
                request = new StringRequest(Request.Method.GET, url, successListener, errorListener) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        } else {
            request = new StringRequest(Request.Method.GET, url, successListener, errorListener);
        }
        setOneTime(request, justOneTime);
        requestQueue.add(request);
    }

    //默认带token,且发送多次
    public static void get(String url, Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        get(url, true, false, successListener, errorListener);
    }

    public static void get(String url, boolean justOneTime, Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        get(url, true, justOneTime, successListener, errorListener);
    }

    public static void get(String url, HashMap<String, String> param, boolean needToken, boolean justOneTime, Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        get(getParm(url, param), needToken, justOneTime, successListener, errorListener);
    }

    public static void get(String url, HashMap<String, String> param, Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        get(url, param, true, false, successListener, errorListener);
    }





    private static String getParm(String url, Map<String, String> parms) {
        StringBuilder stringBuilder = new StringBuilder(url);
        if (parms != null) {
            stringBuilder.append("?");
            for (String key : parms.keySet()) {
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(parms.get(key));
                stringBuilder.append("&");

            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private static String getToken() throws IllegalAccessException {
        TokenBean bean = spUtil.get(StoreConfig.TokenBeanKey, TokenBean.class);
        if (bean == null) {
            throw new IllegalAccessException();
        }
        String accessToke = spUtil.get(StoreConfig.TokenBeanKey, TokenBean.class).accessToken;
        if (TextUtils.isEmpty(accessToke)) {
            throw new IllegalAccessException();
        }
        String sprefreshToken = bean.refreshToken;
        if (TextUtils.isEmpty(sprefreshToken)) {
            throw new IllegalAccessException();
        }
        return accessToke;
    }

    private static void setOneTime(Request request, boolean justoneTime) {
        if (justoneTime) {
            request.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }
}
