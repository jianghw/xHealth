package com.kaurihealth.kaurihealth.services;

import com.example.commonlibrary.widget.bean.TokenBean;

/**
 * Created by Nick on 29/07/2016.
 */
public interface ITokenRefreshService {
    TokenBean refreshToken(TokenBean tokenBean);
}
