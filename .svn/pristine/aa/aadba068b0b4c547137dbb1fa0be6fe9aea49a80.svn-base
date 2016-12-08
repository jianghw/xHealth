package com.kaurihealth.kaurihealth.mine_v.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorEducationDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.mine_p.EnterGraduateSchoolPresenter;
import com.kaurihealth.mvplib.mine_p.IEnterGraduateSchoolView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 我的Fragment-->个人信息-->毕业院校  created by Nick
 * 使用的bean ：DoctorDisplayBean-->DoctorEducationDisplayDto-->EducationHistory
 */
public class EnterGraduateSchoolActivity extends BaseActivity implements IEnterGraduateSchoolView {

    @Inject
    EnterGraduateSchoolPresenter<IEnterGraduateSchoolView> mPresenter;
    @Bind(R.id.tv_more)
    TextView tvMore;
    @Bind(R.id.edit_graduate_school)
    EditText editGraduateSchool;
    @Bind(R.id.ivDelete)
    ImageView ivDelete;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_enter_graduate_school;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.graduate_title_tv_title));
        tvMore.setText(getString(R.string.title_save));

        DoctorEducationDisplayBean doctorEducation = LocalData.getLocalData().getMyself().getDoctorEducation();
        if (doctorEducation != null) {
            //获取毕业院校（教育史）
            setEditGraduateSchool(doctorEducation.getEducationHistory());
            setSelection(editGraduateSchool);
        }


        displayEditConxtent();
        //添加省略号逻辑
//        displayTextConxtent(getEditGraduateSchool());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     */
    @Override
    public void switchPageUI(String className) {
        Intent intent = getIntent();  //初始化intent
        setResult(RESULT_OK, intent);
        finishCur();
    }

    @OnClick({R.id.tv_more, R.id.ivDelete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                mPresenter.onSubscribe();
                break;
            case R.id.ivDelete:
                deleteEditText();
            default:
                break;
        }
    }

    //清除编辑框信息
    private void deleteEditText() {
        clearTextView(editGraduateSchool);
    }

    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        DoctorDisplayBean bean = (DoctorDisplayBean) LocalData.getLocalData().getMyself().clone();
        DoctorEducationDisplayBean doctorEducation = bean.getDoctorEducation();
        if (doctorEducation != null) {
            doctorEducation.setEducationHistory(getEditGraduateSchool());//学历
        }
        return bean;
    }

    public String getEditGraduateSchool() {
        return editGraduateSchool.getText().toString().trim();
    }

    public void setEditGraduateSchool(String editGraduateSchool) {
        this.editGraduateSchool.setText(editGraduateSchool);
    }


    //显示文本框的省略框逻辑
    private void displayTextConxtent(String etGraduateSchool) {
        ViewTreeObserver observer = editGraduateSchool.getViewTreeObserver();  //textAbstract为TextView控件
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver viewObserver = editGraduateSchool.getViewTreeObserver();
                viewObserver.removeGlobalOnLayoutListener(this);
                if (editGraduateSchool.getLineCount() > 0) {
                    int lineEndIndex = editGraduateSchool.getLayout().getLineEnd(0);//设置第1行打省略号
                    String schoolContent = editGraduateSchool.getText().subSequence(0, lineEndIndex - 1) + "...";
                    editGraduateSchool.setText(schoolContent);
                }
            }
        });
    }

    private void displayEditConxtent() {
        ViewTreeObserver observer = editGraduateSchool.getViewTreeObserver();  //textAbstract为TextView控件

        observer.addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                editGraduateSchool.setFocusableInTouchMode(true);
                editGraduateSchool.setFocusable(true);
                editGraduateSchool.requestFocus();
            }
        });
        observer.addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                editGraduateSchool.setFocusableInTouchMode(true);
                editGraduateSchool.setFocusable(true);
                editGraduateSchool.requestFocus();
            }
        });
    }


}
