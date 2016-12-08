package com.kaurihealth.kaurihealth.main_v.NewestVersion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;


import com.kaurihealth.datalib.response_bean.SoftwareInfo;
import com.kaurihealth.mvplib.base_p.Listener;
import com.kaurihealth.utilslib.updata.DownloadManagerUtil;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 张磊 on 2016/4/12.
 * 介绍：
 */
public abstract class NetLastVersionAbstract {
    protected String errorTitle = "错误提示！";
    private String successTitle = "版本更新提示";
    private PackageInfo packageInfo;
    private String fileName = "";

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public void setSuccessTitle(String successTitle) {
        this.successTitle = successTitle;
    }

    /**
     * 获取版本信息的地址
     *
     * @return
     */
    protected String url;
    protected Activity activity;
    protected Context context;
    protected Handler handler;

    public NetLastVersionAbstract(Handler handler, Activity activity, String url) {
        this.handler = handler;
        this.activity = activity;
        this.url = url;
        this.context = activity.getApplicationContext();
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    String className = getClass().getName();

    //直接下载并安装的方式
    public void start() {
        getLastFoftwareInfo(url, new Listener<SoftwareInfo>() {
            @Override
            public void error(String exception) {
                showError(activity, errorTitle, exception);
            }

            @Override
            public void success(final SoftwareInfo response) {
                try {
                    boolean hasLasterVersion = compare(response.VersionName, response.VersionName, context);
                    if (!hasLasterVersion) {
                        return;
                    }
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                    return;
                }
                showSuccess(activity, successTitle, "检测到当前有更新的版本,请问是否更新?",

                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }, new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                downLoad(response.SoftwareDownload, response.VersionName, new Listener<File>() {

                                    @Override
                                    public void error(String exception) {

                                    }

                                    @Override
                                    public void success(File response) {
                                        installAPK(response);
                                    }
                                });
                            }
                        }
                );
            }
        });
    }
    //用downloadManager下载并进行安装

    public void startWithDownloadManager() {
        getLastFoftwareInfo(url, new Listener<SoftwareInfo>() {
            @Override
            public void error(String exception) {
                showError(activity, errorTitle, exception);
            }

            @Override
            public void success(final SoftwareInfo response) {
                try {
                    boolean hasLasterVersion = compare(response.VersionName, response.VersionName, context);
                    if (!hasLasterVersion) {
                        return;
                    }
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                    return;
                }
                showSuccess(activity, successTitle, "检测到当前有更新的版本,请问是否更新?\n" + (response.Comment != null ? response.Comment : " "),

                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }, new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                DownloadManagerUtil.dowload(response.SoftwareDownload, context);
                            }
                        });
            }
        });
    }

    /**
     * 获取当前最新版本信息
     *
     * @param url
     * @param listener
     */
    public abstract void getLastFoftwareInfo(String url, Listener<SoftwareInfo> listener);

    protected abstract boolean compare(String verisonName, String versionCode, Context context) throws IllegalArgumentException;

    protected abstract void downLoad(String uri, String versionName, Listener<File> listener);

    /**
     * @param str
     * @return 返回true的表示全是数字, 返回false表示不全是数字
     */
    protected boolean isDigitsOnly(CharSequence str) {
        final int len = str.length();
        for (int i = 0; i < len; i++) {
            char charOne = str.charAt(i);
            if (i == 0 || i == len - 1) {
                if (!Character.isDigit(charOne)) {
                    return false;
                }
            } else {
                if (!Character.isDigit(charOne) && charOne != '.') {
                    return false;
                }
            }
        }
        return true;
    }

    protected void showError(final Activity activity, final String title, final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE).
                        setTitleText(title).
                        setContentText(message).
                        show();

            }
        });
    }

    protected void showSuccess(final Activity activity, final String title, final String message, final SweetAlertDialog.OnSweetClickListener cancle, final SweetAlertDialog.OnSweetClickListener confirm) {
        if (message == null) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                new SweetAlertDialog(activity,
                        SweetAlertDialog.CUSTOM_IMAGE_TYPE).
                        setTitleText(title).
                        setContentText(message).
                        showCancelButton(true).setCancelText("取消").
                        setCancelClickListener(cancle).setConfirmText("确定").
                        setConfirmClickListener(confirm).
                        show();
            }
        });
    }

    protected String getVersionName() {
        if (packageInfo != null) {
            return packageInfo.versionName;
        } else {
            return null;
        }
    }

    /**
     * 安装软件
     *
     * @return
     */
    protected void installAPK(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    protected int getVersionCode() {
        if (packageInfo != null) {
            return packageInfo.versionCode;
        } else {
            return -1;
        }
    }
}
