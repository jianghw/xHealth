package com.kaurihealth.kaurihealth.clinical_v;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureLikeDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.clinical.adapter.LiteratureCommentAdapter;
import com.kaurihealth.kaurihealth.clinical.util.ClinicalUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.kaurihealth.mvplib.clinical_p.DynamicActivityPresenter;
import com.kaurihealth.mvplib.clinical_p.IDynamicActivityView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/8/29.
 */
public class DynamicActivity extends BaseActivity implements IDynamicActivityView{

    @Bind(R.id.tv_clinicalName)
    TextView tvClinicalName;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.lvContent)
    ListView lvContent;
    /**
     * 点赞
     */
    @Bind(R.id.rb_dz)
    RadioButton rbDz;
    /**
     * 点赞次数
     */
    @Bind(R.id.tv_LikeCount)
    TextView tvLikeCount;
    /**
     * 浏览量
     */
    @Bind(R.id.tv_Browse)
    TextView tvBrowse;
    /**
     * 评论次数
     */
    @Bind(R.id.tv_CommentCount)
    TextView tvCommentCount;

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

    private Bundle bundle;
    private MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean;
    private List<LiteratureCommentDisplayBean> literatureCommentDisplayBeanList;
    private ClinicalUtil clinicalUtil;
    private LiteratureCommentAdapter literatureCommentAdapter;

    public static final int UPDATE = 0;
    private String stringUrl;
    private boolean checked = false;
    private int likeCount;

    @Inject
    DynamicActivityPresenter<IDynamicActivityView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_dynamic_new;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedView() {

        setBack(R.id.iv_back);
        bundle = getBundle();
        clinicalUtil = new ClinicalUtil();
        medicalLiteratureDisPlayBean = (MedicalLiteratureDisPlayBean) bundle.getSerializable("MedicalLiteratureDisPlayBean");
        likeCount = medicalLiteratureDisPlayBean.likeCount;
        tvClinicalName.setText(clinicalUtil.getLengthString(medicalLiteratureDisPlayBean.medicalLiteratureCategory, 6));
        tvLikeCount.setText(medicalLiteratureDisPlayBean.likeCount + "");
        tvBrowse.setText(medicalLiteratureDisPlayBean.browse + "");
        tvCommentCount.setText(medicalLiteratureDisPlayBean.commentCount + "");
        //新增加
        tv_common_title.setText("       "+ medicalLiteratureDisPlayBean.medicalLiteratureTitle);
        tv_time.setText(clinicalUtil.getTimeByTimeFormat(medicalLiteratureDisPlayBean.creatTime,"yyyy-MM-dd HH:mm"));
        tv_from_source.setText(clinicalUtil.getLengthString(medicalLiteratureDisPlayBean.source, 8));
        if (medicalLiteratureDisPlayBean.doctor!=null){
            tv_common_name.setText(medicalLiteratureDisPlayBean.doctor.fullName);
        }else {
            tv_common_name.setText("来源网络");
        }

        stringUrl = clinicalUtil.getUrlString(Url.webPrefix, medicalLiteratureDisPlayBean.medicalLiteratureId);
        //使网页用WebView打开
        setWebView(stringUrl);
        //判断是否点赞
        isSucess();
        literatureCommentDisplayBeanList = new ArrayList<>();
        literatureCommentAdapter = new LiteratureCommentAdapter(this, literatureCommentDisplayBeanList, lvContent,tvCommentCount);
        lvContent.setAdapter(literatureCommentAdapter);
        getData();

    }

    @Override
    public void loadingIndicator(boolean flag) {

    }

    /**
     * 得到当前的临床id
     * @return
     */
    @Override
    public int getCurrentEdicalLiteratureId() {
        return medicalLiteratureDisPlayBean.medicalLiteratureId;
    }


    /**
     * 取消赞
     */
    @Override
    public void DisLikeMedicalLiterature(ResponseDisplayBean response) {
        if (response.isIsSucess()) {
            rbDz.setChecked(false);
            checked = false;
            likeCount = likeCount - 1;
            tvLikeCount.setText(likeCount + "");
        }
    }

    /**
     * 是否点赞状态
     * @param bean
     */
    @Override
    public void checkIsSucess(ResponseDisplayBean bean) {
        /**
         * Bean.isSucess=false >>点赞
         * Bean.isSucess=true >>未点赞
         */
        if (bean.isIsSucess()) {
            //未点赞   将rbDz 设置成为默认未点赞
            rbDz.setChecked(false);
            checked = false;
        } else {
            //已点赞   将rbDz 设置成为点赞
            rbDz.setChecked(true);
            checked = true;
        }
    }

    /**
     * 加载临床评论
     * @param literatureCommentDisplayBeen
     */
    @Override
    public void LoadLiteratureCommentsByMedicalLiteratureId(List<LiteratureCommentDisplayBean> literatureCommentDisplayBeen) {
        if (literatureCommentDisplayBeanList == null)
            throw new IllegalStateException("literatureCommentDisplayBeanList must be not null");
        if (literatureCommentDisplayBeanList.size() > 0)
        //清除数据
        literatureCommentDisplayBeanList.clear();
        //加载数据
        literatureCommentDisplayBeanList.addAll(literatureCommentDisplayBeen);
        literatureCommentAdapter.notifyDataSetChanged();

    }

    /**
     *点赞
     * @param displayBean
     */
    @Override
    public void getCheckMedicalLiteratureLikeResponse(MedicalLiteratureLikeDisplayBean displayBean) {
        if (displayBean != null) {
            rbDz.setChecked(true);
            checked = true;
            likeCount = likeCount + 1;
            tvLikeCount.setText(likeCount + "");
        }
    }

    @Override
    public void switchPageUI(String className) {

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


    /**
     * 点赞和取消赞
     */
    @OnClick(R.id.RL_rb_dz)
    public void onClickrb_dz() {
        if (checked) {
            mPresenter.DisLikeMedicalLiterature();
        }else {
            mPresenter.LikeMedicalLiterature();
        }
    }

    /**
     * 判断是否需要点赞
     */
    private void isSucess() {
        mPresenter.onSubscribe();
    }
    /**
     * 根据临床id得到数据
     */
    public void getData() {
        mPresenter.LoadLiteratureCommentsByMedicalLiteratureId();
    }

    /**
     * 跳转到评论界面
     */
    @OnClick(R.id.tv_comment)
    public void onClick_comment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("MedicalLiteratureDisPlayBean", medicalLiteratureDisPlayBean);
        skipToForResult(CommentActivity.class, bundle, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UPDATE) {
            getData();
        }
    }
}
