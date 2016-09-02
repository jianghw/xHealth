package com.kaurihealth.kaurihealth.services;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.RefreshBean;
import com.kaurihealth.datalib.request_bean.builder.RefreshBeanBuilder;
import com.kaurihealth.datalib.service.ILoginService;
import com.kaurihealth.kaurihealth.util.Interface.IPutter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Nick on 29/07/2016.
 */
public class TokenRefreshService implements ITokenRefreshService {
    private ILoginService loginService;
    private IPutter putter;
    public TokenRefreshService(ILoginService loginService , IPutter putter) {
        this.loginService = loginService;
        this.putter = putter;
    }
    
    public TokenBean refreshToken(TokenBean tokenBean) {
        Date now = Calendar.getInstance().getTime();
        if (tokenBean.expiredDate.before(now)) {
            return tokenBean;
        } else {
            RefreshBean refreshBean = new RefreshBeanBuilder().Build(tokenBean.refreshToken , tokenBean.user.userId);
            Call<TokenBean> call = null;
            try {
                Response<TokenBean> response = call.execute();
                if (response.isSuccessful()) {
                    putter.setTokenBean(response.body());
                    return response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return tokenBean;
            }
            return tokenBean;
        }
    }
}
