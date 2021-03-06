package com.kaurihealth.kaurihealth.casehistory_v;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.SearchBooleanPatientBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.SearchPatientAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.home_p.ISearchpatientFragmentView;
import com.kaurihealth.mvplib.home_p.SearchPatientFragmentPresenter;
import com.kaurihealth.utilslib.widget.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by jianghw .
 * <p/>
 * Describle: 搜索患者
 */
public class SearchPatientActivity extends BaseActivity implements ISearchpatientFragmentView, MaterialSearchBar.OnSearchActionListener {
    @Inject
    SearchPatientFragmentPresenter<ISearchpatientFragmentView> mPresenter;

    @Bind(R.id.searchBar)
    MaterialSearchBar mSearchBar;
    @Bind(R.id.lv_content)
    ListView mLvContent;
    @Bind(R.id.tv_note)
    TextView mTvNote;

    private List<SearchBooleanPatientBean> mBeanList;
    private SearchPatientAdapter adapter;
    /**
     * 搜索内容
     */
    private String editContent;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_search_patient;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn("搜索添加患者");
        mSearchBar.setOnSearchActionListener(this);
    }

    @Override
    protected void initDelayedData() {
        mBeanList = new ArrayList<>();
//        adapter = new SearchPatientAdapter(getContext(), mBeanList);
//        mLvContent.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }


    @Override
    public void switchPageUI(String className) {
//TODO 暂时无
    }

    @Override
    public String getEditTextContent() {
        return editContent;
    }

    @Override
    public void processingData(List<SearchBooleanPatientBean> list) {
        displayErrorDialog("请填写搜索内容");
    }

    @Override
    public void insertPatientSucceed(DoctorPatientRelationshipBean bean) {

    }

    @Override
    public void setEditTextHintContent() {

    }

    /**
     * 搜索
     */
    @Override
    public void onSearchConfirmed(CharSequence text) {
        editContent = text.toString();
        mPresenter.onSubscribe();
    }

    @Override
    public void onSearchStart() {

    }

    @Override
    public void onSearchCompleteBack() {

    }

    @Override
    public void onSearchDeleteBack() {

    }
}
