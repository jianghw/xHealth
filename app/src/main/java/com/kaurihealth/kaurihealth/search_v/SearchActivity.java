package com.kaurihealth.kaurihealth.search_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.request_bean.bean.SearchBooleanResultBean;
import com.kaurihealth.datalib.request_bean.builder.InitialiseSearchRequestBeanBuilder;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.SearchFragmentAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.SearchAtyToFgtEvent;
import com.kaurihealth.mvplib.home_p.ISearchActivityView;
import com.kaurihealth.mvplib.home_p.SearchActivityPresenter;
import com.kaurihealth.utilslib.constant.Global;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jianghw .
 * <p/>
 * Describle: 搜索母页
 */
public class SearchActivity extends BaseActivity implements ISearchActivityView, TextWatcher {
    @Inject
    SearchActivityPresenter<ISearchActivityView> mPresenter;

    @Bind(R.id.etSearch)
    EditText etSearch;
    @Bind(R.id.iv_delete)
    ImageView iv_delete;
    @Bind(R.id.vpContent)
    ViewPager vpContent;
    @Bind(R.id.tablayTop)
    TabLayout tablayTop;

    List<Fragment> fragmentList = new ArrayList<>();//页面
    List<String> searchList = new ArrayList<>();//历史记录
    private SearchFragmentAdapter adapter;
    private SearchPatientFragment patientFragment;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.search_new;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initBackBtn(R.id.tvBack);
        initDelayedView();
    }

    //历史数据初始化
    @Override
    protected void initDelayedData() {
        etSearch.addTextChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeStickyEvent(SearchAtyToFgtEvent.class);
        if (!searchList.isEmpty()) searchList.clear();  //历史记录
        if (!fragmentList.isEmpty()) fragmentList.clear();
        mPresenter.unSubscribe();
    }

    private void initDelayedView() {
        if (fragmentList.size() > 0) fragmentList.clear();
        String[] stringArray;
        Bundle bundle = getBundle();
        if (bundle != null) {
            String bundleString = bundle.getString(Global.Bundle.SEARCH_BUNDLE);
            if (bundleString.equals(Global.Bundle.SEARCH_DOCTOR)) {
                addDoctorFragment();
                stringArray = new String[]{"医生"};
            } else if (bundleString.equals(Global.Bundle.SEARCH_PATIENT)) {
                addPatientFragment();
                stringArray = new String[]{"患者"};
            } else {
                addDefaultAll();
                stringArray = getResources().getStringArray(R.array.search_tab);
            }
            adapter = new SearchFragmentAdapter(getSupportFragmentManager(), fragmentList, stringArray);
            vpContent.setAdapter(adapter);
            vpContent.setOffscreenPageLimit(fragmentList.size() - 1);   //viewPager限定预加载的页面个数
            tablayTop.setupWithViewPager(vpContent);
        }
    }

    private void addDefaultAll() {
        SearchFragment searchFragment = SearchFragment.newInstance();  //所有
        searchFragment.setTitle(Global.Bundle.SEARCH_DEFAULT);
        fragmentList.add(searchFragment);

        SearchHospotalFragment hospotalFragment = SearchHospotalFragment.newInstance();
        hospotalFragment.setTitle(Global.Bundle.SEARCH_HOSPITAL);
        fragmentList.add(hospotalFragment);

        SearchDepartmentFragment departmentFragment = SearchDepartmentFragment.newInstance();
        departmentFragment.setTitle(Global.Bundle.SEARCH_DEPARTMENT);
        fragmentList.add(departmentFragment);

        addDoctorFragment();

        addPatientFragment();
    }

    private void addDoctorFragment() {
        SearchDoctorFragment doctorFragment = SearchDoctorFragment.newInstance();
        doctorFragment.setTitle(Global.Bundle.SEARCH_DOCTOR);
        fragmentList.add(doctorFragment);
    }

    private void addPatientFragment() {
        patientFragment = SearchPatientFragment.newInstance();
        fragmentList.add(patientFragment);

    }

    @Override
    public void switchPageUI(String className) {
//TODO 暂时无
    }

    /**
     * 请求bean
     */
    @Override
    public InitialiseSearchRequestBean getCurrentSearchRequestBean() {
        return new InitialiseSearchRequestBeanBuilder().Build("default", getEditTextSearch());
    }



    @Override
    public void updataDataSucceed(List<SearchBooleanResultBean> been) {
        if (!searchList.contains(getEditTextSearch())) {
            searchList.add(getEditTextSearch());
        }
        EventBus.getDefault().post(new SearchAtyToFgtEvent(been));
    }

    @Override
    public String getEditTextSearch() {
        return etSearch.getText().toString().trim();
    }

    @Override
    public void setEditTextHint() {
        etSearch.setHint(" "+getString(R.string.patient_hint_context));
    }

    @Override
    public String getEditTextHint() {
        String hintcontext = "";
        if (TextUtils.isEmpty(etSearch.getText().toString().trim())){
            hintcontext = etSearch.getHint().toString().trim();
        }
        return hintcontext;
    }

    @Override
    public void setEditDoctorHint() {
        etSearch.setHint(" 请填写搜索信息");
    }

    public List<String> getSearchHistroyList() {
        return searchList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPresenter.onSubscribe();
        }
    }

    /**
     * ··················点击事件··················
     */
    @OnClick({R.id.btnSearch, R.id.iv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                if (editTextIsEmpter()) {
                    mPresenter.onSubscribe();
                }
                hideSoftInput();   //隐藏软键盘
                break;
            case R.id.iv_delete:
                clearTextview(etSearch);
                break;
            default:
                break;
        }
    }

    public boolean editTextIsEmpter() {
        if (getEditTextSearch() == null || getEditTextSearch().isEmpty() || getEditTextSearch().length() < 1) {
            displayErrorDialog("请输入搜索关键字");
            return false;
        }
        return true;
    }

    /**
     * 历史记录
     */
    public void onSearchHistory(String content) {
        etSearch.setText(content);
        mPresenter.onSubscribe();//搜索
    }

    /**
     * 文本输入监听
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(etSearch.getText().toString().trim()))
            EventBus.getDefault().post(new SearchAtyToFgtEvent(null));
        if (patientFragment!=null&&patientFragment.getUserVisibleHint()) {
            setEditTextHint();
        }else {
            setEditDoctorHint();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 跳转患者界面
     */
    public void skipPatientFragment() {
        setResult(RESULT_OK);
        finishCur();
    }
}
