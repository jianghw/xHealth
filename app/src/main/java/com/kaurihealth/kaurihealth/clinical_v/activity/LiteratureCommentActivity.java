package com.kaurihealth.kaurihealth.clinical_v.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.clinical_v.Utils.ClinicalUtil;
import com.kaurihealth.mvplib.clinical_p.ILiteratureCommentActivityView;
import com.kaurihealth.mvplib.clinical_p.LiteratureCommentActivityPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/8/31.
 */
public class LiteratureCommentActivity extends BaseActivity implements ILiteratureCommentActivityView{

    @Bind(R.id.tv_medicalLiteratureTitle)
    TextView tvMedicalLiteratureTitle;
    @Bind(R.id.edt_comment)
    EditText edtComment;

    private Bundle bundle;
    private LiteratureCommentDisplayBean literatureCommentDisplayBean;



    @Inject
    LiteratureCommentActivityPresenter<ILiteratureCommentActivityView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_literature_comment;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {

        bundle = getBundle();
        literatureCommentDisplayBean = (LiteratureCommentDisplayBean) bundle.getSerializable("LiteratureCommentDisplayBean");
        tvMedicalLiteratureTitle.setText(new ClinicalUtil().getLengthString(literatureCommentDisplayBean.literatureCommentContent, 10));
    }

    @Override
    public void switchPageUI(String className) {

    }

    @OnClick(R.id.tv_register)
    public void onClick_restore() {
       mPresenter.onSubscribe();

    }

    @Override
    public String getComment() {
        return edtComment.getText().toString().trim();
    }

    @Override
    public int getLiteratureCommentId() {
        return literatureCommentDisplayBean.literatureCommentId;
    }

    @Override
    public void getLiteratureReplyDisplayBean(LiteratureReplyDisplayBean bean) {
        if (bean != null) {
            setResult(DynamicActivity.UPDATE);
            finishCur();
        }
    }
}
