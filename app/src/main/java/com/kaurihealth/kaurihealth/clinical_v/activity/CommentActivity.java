package com.kaurihealth.kaurihealth.clinical_v.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.clinical_v.Utils.ClinicalUtil;
import com.kaurihealth.mvplib.clinical_p.CommentActivityPresenter;
import com.kaurihealth.mvplib.clinical_p.ICommentActivityView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/8/30.
 */
public class CommentActivity extends BaseActivity implements ICommentActivityView{

    @Bind(R.id.tv_medicalLiteratureTitle)
    TextView tvMedicalLiteratureTitle;
    @Bind(R.id.edt_comment)
    EditText edtComment;

    private Bundle bundle;
    private MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean;

    private String comment;

    @Inject
    CommentActivityPresenter<ICommentActivityView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);
        bundle = getBundle();
        medicalLiteratureDisPlayBean = (MedicalLiteratureDisPlayBean) bundle.getSerializable("MedicalLiteratureDisPlayBean");
        tvMedicalLiteratureTitle.setText(new ClinicalUtil().getLengthString(medicalLiteratureDisPlayBean.medicalLiteratureTitle, 10));
    }

    @OnClick(R.id.tv_register)
    public void onClick_restore() {
        mPresenter.onSubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    public void switchPageUI(String className) {

    }

    /**
     * 得到评论
     * @return
     */
    @Override
    public String getComment() {
        return comment = edtComment.getText().toString().trim();
    }

    /**
     * 得到当前临床id
     * @return
     */
    @Override
    public int getCurrentMedicalLiteratureId() {
        return medicalLiteratureDisPlayBean.medicalLiteratureId;
    }

    //评论插入成功后跳转到CommentActivity
    @Override
    public void getLiteratureCommentDisplayBean(LiteratureCommentDisplayBean bean) {
        if (bean != null) {
            setResult(DynamicActivity.UPDATE);
            finishCur();
        }
    }

}
