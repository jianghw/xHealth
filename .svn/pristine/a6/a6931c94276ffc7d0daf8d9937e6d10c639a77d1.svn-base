package com.kaurihealth.kaurihealth.clinical_v.activity;

import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.clinical_v.Utils.ClinicalUtil;
import com.kaurihealth.kaurihealth.clinical_v.adapter.LiteratureCommentAdapter;
import com.kaurihealth.kaurihealth.eventbus.ClinicalRefreshEvent;
import com.kaurihealth.kaurihealth.main_v.NewestVersion.Url;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.kaurihealth.util.DateConvertUtils;
import com.kaurihealth.mvplib.clinical_p.DynamicNewPresenter;
import com.kaurihealth.mvplib.clinical_p.IDynamicNewView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mip on 2016/11/23.
 */

public class DynamicActivityNew extends BaseActivity implements IDynamicNewView{

    @Bind(R.id.dy_webview)
    WebView webView;
    /**
     * 标题
     */
    @Bind(R.id.tv_common_title)
    TextView tv_common_title;

    /**
     * 创建时间
     */
    @Bind(R.id.tv_time)
    TextView tv_time;
    /**
     * 来源
     */
    @Bind(R.id.tv_from_source)
    TextView tv_from_source;
    /**
     * 作者
     */
    @Bind(R.id.tv_common_name)
    TextView tv_common_name;

    @Bind(R.id.top)
    LinearLayout top;


    private int likeCount;
    private Bundle bundle;
    private MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean;
    private List<LiteratureCommentDisplayBean> literatureCommentDisplayBeanList;
    private ClinicalUtil clinicalUtil;

    private LiteratureCommentAdapter literatureCommentAdapter;
    public static final int UPDATE = 0;
    private String stringUrl;
    private boolean checked = false;

    private List<LiteratureReplyDisplayBean> literatureReplyDisplayBeen = new ArrayList<>();


    @Inject
    DynamicNewPresenter<IDynamicNewView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.dynamic_new;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initDate();//初始化数据

    }

    private void initDate() {
        initBackBtn(R.id.iv_back);
        bundle = getBundle();
        clinicalUtil = new ClinicalUtil();
        medicalLiteratureDisPlayBean = (MedicalLiteratureDisPlayBean) bundle.getSerializable("MedicalLiteratureDisPlayBean");
        likeCount = medicalLiteratureDisPlayBean.likeCount;
        //新增加
        tv_common_title.setText("       " + medicalLiteratureDisPlayBean.medicalLiteratureTitle);
        tv_time.setText(DateConvertUtils.getWeekOfDate(null, medicalLiteratureDisPlayBean.creatTime));
        tv_from_source.setText(clinicalUtil.getLengthString(medicalLiteratureDisPlayBean.source, 8));
        if (medicalLiteratureDisPlayBean.doctor != null) {
            tv_common_name.setText(medicalLiteratureDisPlayBean.doctor.fullName);
        } else {
            tv_common_name.setText("来源网络");
        }

        stringUrl = clinicalUtil.getUrlString(Url.webPrefix, medicalLiteratureDisPlayBean.medicalLiteratureId);
        //使网页用WebView打开
        setWebView(stringUrl);
        //判断是否点赞
        literatureCommentDisplayBeanList = new ArrayList<>();

        EventBus.getDefault().postSticky(new ClinicalRefreshEvent());//返回后界面刷新

    }

    /**
     * 使网页用WebView打开
     *
     * @param stringUrl
     */
    private void setWebView(String stringUrl) {
        webView.loadUrl(stringUrl);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setDefaultTextEncodingName("UTF-8");
        mWebSettings.setLoadsImagesAutomatically(true);
        //新增加
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setDisplayZoomControls(false);

        mWebSettings.setDomStorageEnabled(true);
    }


    @Override
    public void switchPageUI(String className) {

    }
}
