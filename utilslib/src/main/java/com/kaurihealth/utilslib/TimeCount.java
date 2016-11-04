package com.kaurihealth.utilslib;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by 张磊 on 2016/2/3.
 * 介绍：
 * 主要为了注册的时候获取验证码，这个时候进行倒计时
 */
public class TimeCount extends CountDownTimer {
    TextView tvShow;
    String formatStr;
    String end;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimeCount(long millisInFuture, long countDownInterval, TextView tvShow, String formatStr, String end) {
        super(millisInFuture, countDownInterval);
        this.tvShow = tvShow;
        this.formatStr = formatStr;
        this.end = end;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tvShow.setEnabled(false);
        tvShow.setText(String.format(formatStr, (int) (millisUntilFinished / 1000)));
    }

    @Override
    public void onFinish() {
        tvShow.setText(end);
        tvShow.setEnabled(true);
    }
}
