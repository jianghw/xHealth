package com.kaurihealth.kaurihealth.clinical.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.builder.NewLiteratureReplyDisplayBeanBuilder;
import com.kaurihealth.datalib.service.ILiteratureReplyService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.clinical.util.ClinicalUtil;
import com.kaurihealth.kaurihealth.clinical_v.DynamicActivity;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiteratureCommentActivity extends CommonActivity {

    @Bind(R.id.tv_medicalLiteratureTitle)
    TextView tvMedicalLiteratureTitle;
    @Bind(R.id.edt_comment)
    EditText edtComment;

    private Bundle bundle;
    private LiteratureCommentDisplayBean literatureCommentDisplayBean;
    private ILiteratureReplyService iLiteratureReplyService;
    private IGetter getter;
    private NewLiteratureReplyDisplayBean newLiteratureReplyDisplayBean;

    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_literature_comment);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        bundle = getBundle();
        getter = Getter.getInstance(getApplicationContext());
        iLiteratureReplyService = new ServiceFactory(Url.prefix,getApplicationContext()).getLiteratureReplyService();
        literatureCommentDisplayBean = (LiteratureCommentDisplayBean) bundle.getSerializable("LiteratureCommentDisplayBean");
        tvMedicalLiteratureTitle.setText(new ClinicalUtil().getLengthString(literatureCommentDisplayBean.literatureCommentContent, 10));
    }

    @OnClick(R.id.tv_register)
    public void onClick_restore() {
        comment = edtComment.getText().toString().trim();
        newLiteratureReplyDisplayBean = new NewLiteratureReplyDisplayBeanBuilder().Builder(literatureCommentDisplayBean.literatureCommentId, comment);
        Call<LiteratureReplyDisplayBean> listCall = iLiteratureReplyService.InsertLiteratureReply(newLiteratureReplyDisplayBean);
        listCall.enqueue(new Callback<LiteratureReplyDisplayBean>() {
            @Override
            public void onResponse(Call<LiteratureReplyDisplayBean> call, Response<LiteratureReplyDisplayBean> response) {
                if (response.isSuccessful()) {
                    LiteratureReplyDisplayBean bean = response.body();
                    if (bean != null) {
                        setResult(DynamicActivity.UPDATE);
                        finishCur();
                    }
                }
            }

            @Override
            public void onFailure(Call<LiteratureReplyDisplayBean> call, Throwable t) {
                showToast(LoadingStatu.NetError.value);
            }
        });
    }
}
