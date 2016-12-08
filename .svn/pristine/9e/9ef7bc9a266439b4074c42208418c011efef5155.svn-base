package com.kaurihealth.kaurihealth;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.avos.avoscloud.AVOSCloud;
import com.kaurihealth.kaurihealth.welcome_v.WelcomeActivity;
import com.kaurihealth.utilslib.log.LogUtils;

import org.json.JSONObject;

/**
 * Created by jianghw on 2016/10/25.
 * <p/>
 * Describe:
 */

public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals("com.pushdemo.action")) {
                JSONObject json = new JSONObject(intent.getExtras().getString("com.avos.avoscloud.Data"));
                LogUtils.jsonDate(json);
                final String message = json.getString("alert");
                Intent resultIntent = new Intent(AVOSCloud.applicationContext, WelcomeActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(AVOSCloud.applicationContext, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(AVOSCloud.applicationContext)
                        .setSmallIcon(R.mipmap.ic_logo_top)
                        .setContentTitle("之久和司机发生")
                        .setContentText(message)
                        .setTicker(message);
                mBuilder.setContentIntent(pendingIntent);
                mBuilder.setAutoCancel(true);

                int mNotificationId = 10086;
                NotificationManager mNotifyMgr = (NotificationManager) AVOSCloud.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
