package com.kaurihealth.datalib.request_bean.bean;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Nick on 23/04/2016.
 */
public class RegisterResponse {
    /// <summary>
    /// 消息
    /// </summary>
    public String message;
    /// <summary>
    /// 是否成功
    /// </summary>
    public boolean isSuccessful;

    /// <summary>
    /// 令牌DTO
    /// </summary>
    @JsonIgnoreProperties
    public TokenBean token;
}
