package com.kaurihealth.datalib.response_bean;



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

    public TokenBean token;
}
