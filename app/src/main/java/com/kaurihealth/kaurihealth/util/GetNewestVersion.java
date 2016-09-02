package com.kaurihealth.kaurihealth.util;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.bugtags.library.Bugtags;
import com.kaurihealth.datalib.service.ICheckVersionService;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.example.commonlibrary.widget.bean.SoftwareInfo;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.youyou.zllibrary.thread.ThreadFactory;
import com.youyou.zllibrary.updata.Listener;
import com.youyou.zllibrary.updata.NetLastVersionAbstract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by 张磊 on 2016/4/12.
 * 介绍：
 * 参考地址：http://bpsinghrajput.blogspot.jp/2012/07/how-to-download-and-install-apk-from.html
 */
public class GetNewestVersion extends NetLastVersionAbstract {


    private ServiceFactory serviceFactory;
    private ICheckVersionService checkVersionService;

    public GetNewestVersion(Handler handler, Activity activity, String url) {
        super(handler, activity, url);
    }

    private String prefixName = "佳仁健康";

    //已经修改retrofit  miping
    @Override
    public void getLastFoftwareInfo(String url, final Listener<SoftwareInfo> listener) {
        String versionEnvironmentType;
        switch (Url.Environment) {
            case "Test":
                versionEnvironmentType = "Test";
                break;
            case "Develop":
                versionEnvironmentType = "Internal";
                break;
            case "Preview":
                versionEnvironmentType = "Preview";
                break;
            case "Stable":
                versionEnvironmentType = "Official";
                break;
            default:
                return;
        }
        Map<String, String> param = new HashMap<>();
        param.put("versionEnvironmentType", versionEnvironmentType);


        serviceFactory = new ServiceFactory(Url.LoadAndroidVersionWithDoctor,activity.getApplicationContext());
        checkVersionService = serviceFactory.getCheckVersionService();
        Call<SoftwareInfo> SoftwareInfCall = checkVersionService.CheckVersion(param);

        SoftwareInfCall.enqueue(new Callback<SoftwareInfo>() {
            @Override
            public void onResponse(Call<SoftwareInfo> call, retrofit2.Response<SoftwareInfo> response) {
                if (response.isSuccessful()) {
                    SoftwareInfo softwareInfo = response.body();
                    listener.success(softwareInfo);
                }
            }


            @Override
            public void onFailure(Call<SoftwareInfo> call, Throwable t) {
                Bugtags.sendException(t);
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                listener.error(LoadingStatu.NetError.value);
            }
        });
    }


    @Override
    protected boolean compare(String verisonName, String versionCode, Context context) throws IllegalArgumentException {
        String versionNameLocal = getVersionName();
        if (versionNameLocal == null || !isDigitsOnly(versionNameLocal) || !isDigitsOnly(verisonName)) {
            throw new IllegalArgumentException();
        }
        float versionNameLocalF = Float.parseFloat(versionNameLocal);
        float verisonNameF = Float.parseFloat(verisonName);
        if (verisonNameF > versionNameLocalF) {
            return true;
        } else {
            return false;
        }
    }

    private InputStream inputStream;
    private FileOutputStream fileOutputStream;

    @Override
    protected void downLoad(final String uri, final String versionName, final Listener<File> listener) {
        if (TextUtils.isEmpty(uri) || TextUtils.isEmpty(versionName)) {
            showError(activity, errorTitle, "下载失败");
            return;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(uri);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "text/html");
                    urlConnection.setRequestProperty("Host", "139.196.196.143:83");
                    urlConnection.setRequestProperty("Connection", "keep-alive");
                    urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
//                    urlConnection.setDoOutput(true);
                    urlConnection.connect();
                    File sdCard = Environment.getExternalStorageDirectory();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                    String timeStr = format.format(new Date());
                    File file = new File(sdCard, prefixName + versionName + timeStr + ".apk");
                    if (!file.exists()) {
                        if (!file.createNewFile()) {
                            showError(activity, errorTitle, "创建文件失败");
                            return;
                        }
                    }
                    fileOutputStream = new FileOutputStream(file);
                    int status = urlConnection.getResponseCode();
                    Log.d(className, status + "");
                    if (status >= 400) {
                        inputStream = urlConnection.getErrorStream();
                        showError(activity, errorTitle, "服务端有问题");
                    } else {
                        inputStream = urlConnection.getInputStream();
                        byte[] buffer = new byte[1024];
                        int bufferLength = 0;
                        while ((bufferLength = inputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, bufferLength);
                        }
                        listener.success(file);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Bugtags.sendException(e);
                    showError(activity, errorTitle, "下载失败");
                } catch (IOException e) {
                    e.printStackTrace();
                    Bugtags.sendException(e);
                    showError(activity, errorTitle, "下载失败");
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Bugtags.sendException(e);
                        }
                    }
                }
            }
        };
        ThreadFactory.getThread(runnable).start();
    }

    String className = getClass().getName();
}
