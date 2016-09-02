package com.kaurihealth.kaurihealth.clinical.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.builder.NewLiteratureCommentDisplayBeanBuilder;
import com.kaurihealth.datalib.service.ILiteratureCommentService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.clinical.util.ClinicalUtil;
import com.kaurihealth.kaurihealth.clinical_v.DynamicActivity;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends CommonActivity {

    @Bind(R.id.tv_medicalLiteratureTitle)
    TextView tvMedicalLiteratureTitle;
    @Bind(R.id.edt_comment)
    EditText edtComment;

    private Bundle bundle;
    private MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean;
    private ILiteratureCommentService iLiteratureCommentService;
    private NewLiteratureCommentDisplayBean newLiteratureCommentDisplayBean;

    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        bundle = getBundle();
        iLiteratureCommentService = new ServiceFactory(Url.prefix,getApplicationContext()).getILiteratureCommentService();
        medicalLiteratureDisPlayBean = (MedicalLiteratureDisPlayBean) bundle.getSerializable("MedicalLiteratureDisPlayBean");
        tvMedicalLiteratureTitle.setText(new ClinicalUtil().getLengthString(medicalLiteratureDisPlayBean.medicalLiteratureTitle, 10));
    }

    @Override
    public void hideSoftInput() {
        View currentFocus = getCurrentFocus();
        if (currentFocus!=null) {
        }
    }

    @OnClick(R.id.tv_register)
    public void onClick_restore() {
        comment = edtComment.getText().toString().trim();
        newLiteratureCommentDisplayBean = new NewLiteratureCommentDisplayBeanBuilder().Builder(medicalLiteratureDisPlayBean.medicalLiteratureId, comment);
        Call<LiteratureCommentDisplayBean> listCall = iLiteratureCommentService.InsertLiteratureComment(newLiteratureCommentDisplayBean);
        listCall.enqueue(new Callback<LiteratureCommentDisplayBean>() {
            @Override
            public void onResponse(Call<LiteratureCommentDisplayBean> call, Response<LiteratureCommentDisplayBean> response) {
                if (response.isSuccessful()) {
                    LiteratureCommentDisplayBean bean = response.body();
                    if (bean != null) {
                        setResult(DynamicActivity.UPDATE);
                        finishCur();
                    }
                }
            }

            @Override
            public void onFailure(Call<LiteratureCommentDisplayBean> call, Throwable t) {
                showToast(LoadingStatu.NetError.value);
            }
        });
    }

}
