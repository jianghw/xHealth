package com.kaurihealth.datalib.remote;

import android.support.annotation.NonNull;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.utilslib.constant.Global;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import retrofit2.Retrofit;

/**
 * Created by jianghw on 2016/8/18.
 * <p/> Retrofit生产工厂 单利
 * 描述：
 */
public class RetrofitFactory {
    private String environment = null;
    private String baseUrl = null;
    private SSLSocketFactory sslSocketFactory;

    public static RetrofitFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
    }

    /**
     * 获取Environment 信息
     *
     * @return
     */
    private String getCurEnvironment() {
        if (environment != null) return environment;
        environment = LocalData.getLocalData().getEnvironment();
        return environment;
    }

    /**
     * 基础地址api
     *
     * @return
     */
    @NonNull
    private String getBaseUrl() {
        if (baseUrl != null) return baseUrl;
        switch (getCurEnvironment()) {
            case Global.Environment.DEVELOP:
                baseUrl = Global.Url.DEVELOP_URL;
                break;
            case Global.Environment.TEST:
                baseUrl = Global.Url.TEST_URL;
                break;
            case Global.Environment.PREVIEW:
                baseUrl = Global.Url.PREVIEW_URL;
                break;
            default:
                baseUrl = Global.Url.PREVIEW_URL;
                break;
        }
        return baseUrl;
    }

    //医生端检查更新的地址
    public static String LoadAndroidVersionWithDoctorUrl = "http://101.201.151.189:8080/";

    public Retrofit createRetrofit(String requirements) {
        switch (requirements) {
            case Global.Factory.LoadAndroidVersionWithDoctor:
                return NoTokenRetrofit.getInstance().createRetrofit(LoadAndroidVersionWithDoctorUrl);
            case Global.Factory.NO_TOKEN:
                return NoTokenRetrofit.getInstance().createRetrofit(getBaseUrl());
            case Global.Factory.NO_TOKEN_EXECUTE:
                return new NoTokenExecuteRetrofit().createRetrofit(getBaseUrl());
            case Global.Factory.DEFAULT_TOKEN:
                return DefaultRetrofit.getInstance().createRetrofit(getBaseUrl());
            default:
                return DefaultRetrofit.getInstance().createRetrofit(getBaseUrl());
        }
    }

    SSLSocketFactory getSslSocketFactory() {
        if (sslSocketFactory != null) return sslSocketFactory;
        sslSocketFactory = getCompanySocketFactory(createInputStreamList());
        return sslSocketFactory;
    }

    private SSLSocketFactory getCompanySocketFactory(List<InputStream> certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance("BKS");
            for (int i = 0, size = certificates.size(); i < size; ) {
                InputStream certificate = certificates.get(i);
                String certificateAlias = Integer.toString(i++);

                keyStore.load(null, null);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                if (certificate != null) certificate.close();
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(keyStore);

            TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(trustManagerFactory.getTrustManagers());
            sslContext.init(null, wrappedTrustManagers, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<InputStream> createInputStreamList() {
        List<InputStream> certificates = new ArrayList<>();
        String cer = "-----BEGIN CERTIFICATE-----\n" +
                "MIIFlDCCA3ygAwIBAgIQAViMOjUHs/iXIxx2t++F3TANBgkqhkiG9w0BAQsFADBGMQswCQYDVQQG\n" +
                "EwJDTjEaMBgGA1UEChMRV29TaWduIENBIExpbWl0ZWQxGzAZBgNVBAMMEkNBIOayg+mAmuagueiv\n" +
                "geS5pjAeFw0xNDExMDgwMDU4NThaFw0yOTExMDgwMDU4NThaME8xCzAJBgNVBAYTAkNOMRowGAYD\n" +
                "VQQKExFXb1NpZ24gQ0EgTGltaXRlZDEkMCIGA1UEAwwbQ0Eg5rKD6YCa5YWN6LS5U1NM6K+B5Lmm\n" +
                "IEcyMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0JMBh1z5ihKZLtSptut+daPq6CIo\n" +
                "vna/2uswQNsecCE4v+pEZ5djMblHQwzu1ciAav06jGTK7c6lRB4Sbb82hoXUUnZ5jBaoRDNPM3Na\n" +
                "griXz06P3Mm2FJXtzWKT73Nw4gu+TnbdtLildjf7EOCpZFUC76e8svcOtfJpRR0dDSui5xNH7E1z\n" +
                "EnJBCL3hvyVnhWu6KWfKyiAHL25IEK1bEQmHAZCePcJpeiSDPE9URMF8iiurgXivk2Hi9NKKHOBy\n" +
                "Kr0AlNUxWM0qjTv5FivrmrqvFnF5+oPVcZUWyDbEocauums8RjgYaqaD2qv7mCU3uaOzCWIijCPa\n" +
                "dMXtHXUqOwIDAQABo4IBczCCAW8wDgYDVR0PAQH/BAQDAgEGMB0GA1UdJQQWMBQGCCsGAQUFBwMC\n" +
                "BggrBgEFBQcDATASBgNVHRMBAf8ECDAGAQH/AgEAMC8GA1UdHwQoMCYwJKAioCCGHmh0dHA6Ly9j\n" +
                "cmxzMi53b3NpZ24uY24vY2EyLmNybDBwBggrBgEFBQcBAQRkMGIwJgYIKwYBBQUHMAGGGmh0dHA6\n" +
                "Ly9vY3NwMi53b3NpZ24uY24vY2EyMDgGCCsGAQUFBzAChixodHRwOi8vYWlhMi53b3NpZ24uY24v\n" +
                "Y2EyZzItc2VydmVyMS1mcmVlLmNlcjAdBgNVHQ4EFgQUMNp0hvMokFae1zExwr1ZzZMSOR0wHwYD\n" +
                "VR0jBBgwFoAU4E2/3JtBXRPoZPCn6RWk4YHBujEwRwYDVR0gBEAwPjA8Bg0rBgEEAYKbUQYBAgIC\n" +
                "MCswKQYIKwYBBQUHAgEWHWh0dHA6Ly93d3cud29zaWduLmNvbS9wb2xpY3kvMA0GCSqGSIb3DQEB\n" +
                "CwUAA4ICAQBbCJgtcYOHa2WELfGAcTXZawWomqwm56yyVnowYt+4F5chlEdx8SRuaYPzurYbMJuY\n" +
                "0u1fs7NqmFw/A/oXbNy0j37h2fOWVVVayGQIPtw8i3yAaIGTyEp5lHQhjKebBq6SyRzSyrzQzWNs\n" +
                "t8pZlLH51m3KR1vs2zXPWcXmu3zyAc7p+Bghv0N//wo98IKJSXHscLMh5bXBVQLyd/et/YnN2MzH\n" +
                "XJHN5ZrSby8P4WAWtr2AC0bl3922KUnzHCBKIM4VAxNXOhuXt5pn1yoyKbFh/xGbpnMtFBud5KEP\n" +
                "CPR7umQRiESRYsLyaMx0INAHknGLevWUKdAF9PTX5Yt+LIwNiYmZsIJYCA2vEmhXWoLaz6xKySt6\n" +
                "jDhG2ITIuuz6dY5sJMV5eDFnrWMAR3qTHBy5TGF4nWtsHuADhx0vkTdlodVxZaDI5VuNDjCfhAvM\n" +
                "pDvMQc5I4CuSLTtyzUMMsIKSxlG58FQ/S0nVfqQlpJDzt+7pSW1fb0wFtyQjVsUymi/o0Q8CNJxv\n" +
                "KGDeQiAvYwsjd1kWxKpiV+uXcqLlPUTdnv5jP7oNE2uLrCk64HjrrqEDIShimAuTKxXpNUbknxGe\n" +
                "hrtEIQcXfsl6MDJhmPuwx/9tzdu9uOYyE9MX9CFzVIWohrOAOVFtWcYTzi/LhyTDIZebcHNeaxdj\n" +
                "edEoLB44oQ==\n" +
                "-----END CERTIFICATE-----";
        InputStream inputStream = new ByteArrayInputStream(cer.getBytes());
        certificates.add(inputStream);
        return certificates;
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                try {
                    originalTrustManager.checkClientTrusted(chain, authType);
                } catch (CertificateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                try {
                    originalTrustManager.checkServerTrusted(chain, authType);
                } catch (CertificateException e) {
                    e.printStackTrace();
                }
            }

            public X509Certificate[] getAcceptedIssuers() {
                return originalTrustManager.getAcceptedIssuers();
            }
        }
        };
    }

    //医生端检查更新的地址
    public static String LoadAndroidVersionWithDoctor = "http://101.201.151.189:8080/";
}
