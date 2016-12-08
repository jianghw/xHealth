package com.kaurihealth.kaurihealth.receiver_test;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.kaurihealth.kaurihealth.R;

/**
 * Created by Garnet_Wu on 2016/11/30.
 * 描述: 自定义发送广播
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    public  static  final  int  NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"我已经接受到你的广播消息了",Toast.LENGTH_LONG).show();

        //获取NotificationManager的实例
        NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(R.drawable.ic_launcher)//设置通知小ICON
                .setAutoCancel(true);       //设置这个标志当用户单击面板就可以让通知将自动取消
                //当通知被点击的时候, 就执行这个被设置的Intent
        Intent intent1 = new Intent(context, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pendingIntent);



        //  .setNumber(number) //设置通知集合的数量

        //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission

        Notification notification = mBuilder.build();


        notifyManager.notify(NOTIFICATION_ID,notification);

    }
}
