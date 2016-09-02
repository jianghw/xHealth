package com.youyou.zllibrary.updata;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;

import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;

/**
 * Created by 张磊 on 2016/5/24.
 * 介绍：
 */
public class DownloadManagerUtil {
    private static LogUtilInterface logUtil = LogFactory.getSimpleLog(DownloadManagerUtil.class.getName());

    /**
     * 获得下载器
     *
     * @param context
     * @return
     */
    private static DownloadManager getDownLoadManager(Context context) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        return downloadManager;
    }

    /**
     * 用downloadManager
     *
     * @param downloadManager
     * @param uri
     * @return
     */
    private static long downLoad(DownloadManager downloadManager, Uri uri, String title) {
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(title);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        return downloadManager.enqueue(request);
    }

    /**
     * 注册广播接收者
     */
    private static void registeDownloadBroadcastReceiver(final long downloadID, Context context, final IOnReceive onReceive) {
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (reference == downloadID) {
                    onReceive.onReceive(context, intent, downloadID);
                }
                context.unregisterReceiver(this);
            }
        };

        context.registerReceiver(receiver, intentFilter);
    }

    private static void installApk(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        logUtil.i(uri.toString());
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private interface IOnReceive {
        void onReceive(Context context, Intent intent, long reference);
    }

    public static void dowload(String uriStr, Context context) {
        Uri uri = Uri.parse(uriStr);
        final DownloadManager downLoadManager = getDownLoadManager(context);
        long downReference = downLoad(downLoadManager, uri, "佳仁健康apk正在下载中。。。");
        registeDownloadBroadcastReceiver(downReference, context, new IOnReceive() {
            @Override
            public void onReceive(Context context, Intent intent, long reference) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(reference);
                Cursor cursor = downLoadManager.query(query);
                if (cursor.moveToFirst()) {
                    int columIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (cursor.getInt(columIndex) == DownloadManager.STATUS_SUCCESSFUL) {
                        String uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                        logUtil.i("下载成功路径：" + uri);
                        installApk(context,Uri.parse("file://"+uri));
                    }
                }
            }
        });
    }
}
