package com.youyou.zllibrary.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 张磊 on 2016/2/4.
 * 介绍：
 */
public class BroadcastFactory {


    /**
     * 获取短信接收者
     *
     * @param tvChange
     */
    public static BroadcastReceiver getSmsReceiver(final TextView tvChange) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] objects = (Object[]) bundle.get("pdus");
                    List<SmsMessage> smsMessages = new ArrayList<>();
                    SmsMessage smsRecent = null;
                    for (Object iteam : objects) {
                        SmsMessage sms = SmsMessage.createFromPdu((byte[]) iteam);
                        String content = sms.getDisplayMessageBody();
                        if (content.startsWith("【佳仁健康】")) {
                            if (smsRecent == null) {
                                smsRecent = sms;
                            } else {
                                if (sms.getTimestampMillis() > smsRecent.getTimestampMillis()) {
                                    smsRecent = sms;
                                }
                            }
                        }
                    }
                    if (smsRecent != null) {
                        String content = smsRecent.getDisplayMessageBody();
                        String regex = "\\d{4,}";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(content);
                        if (matcher.find()) {
                            tvChange.setText(content.substring(matcher.start(), matcher.end()));
                        }
                    }
                }
            }
        };
        return broadcastReceiver;
    }

    /**
     * 获取短信intent
     */
    public static IntentFilter getSmsIntentFileter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(999);
        return filter;
    }
}
