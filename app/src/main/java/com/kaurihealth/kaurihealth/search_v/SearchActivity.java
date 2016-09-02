package com.kaurihealth.kaurihealth.search_v;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.request_bean.builder.InitialiseSearchRequestBeanBuilder;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.home.adapter.ModelPagerAdapter;
import com.kaurihealth.kaurihealth.home.util.PagerManager;
import com.kaurihealth.mvplib.search_p.ISearchView;
import com.kaurihealth.mvplib.search_p.SearchPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import github.chenupt.springindicator.SpringIndicator;
//import github.chenupt.springindicator.SpringIndicator;

/**
 * Created by Garnet_Wu on 2016/8/23.
 */
public class SearchActivity extends BaseActivity implements ISearchView, Validator.ValidationListener {

    @Bind(R.id.iv_delete)
    ImageView ivDeleteText;

    @NotEmpty(message = "请输入您想搜索的内容！")
    @Bind(R.id.etSearch)
    EditText etSearch;


    @Inject
    SearchPresenter<ISearchView> mSearchPresenter;
    private Validator validator;
    private ViewPager vp_content;
    private  Fragment[] fragments;
    private String[] indicators =  {"所有", "医生", "科室", "医院", "患者"};
    //用于关键字搜索(里面元素就是type)
    private  String[] keyWords = {"default", "doctor", "hospital", "department", "patient"};
    private ModelPagerAdapter adapter;
    private String content;
    private int index;

    //BaseActivity 返回布局id
    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_search;
    }

    //初始化presenter和数据
    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mSearchPresenter.setPresenter(this);
    }

    //初始化表单验证器
    @Override
    protected void initDelayedView() {
        validator = new Validator(this);
        validator.setValidationListener(this);
        ivDeleteText.setVisibility(View.GONE);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    ivDeleteText.setVisibility(View.GONE);
                } else {
                    ivDeleteText.setVisibility(View.VISIBLE);
                }
            }
        });

        //添加fragment
        SpringIndicator indicator = (SpringIndicator) findViewById(R.id.indicator);
        vp_content = (ViewPager) findViewById(R.id.vp_content);  //找到ViewPager控件
        Fragment fragment0 = new BlankAllFragment();//所有
        Fragment fragment1 = new BlankDoctorFragment();//医生
        Fragment fragment2 = new BlankHospitalFragment();//医院
        Fragment fragment3 = new BlankMedicineFragment();//科室
        BlankPatientFragment fragment4 = new BlankPatientFragment();
        //这句话干嘛的???
        //fragment4.setSkip(skip);
        fragments = new Fragment[]{fragment0,fragment1,fragment2,fragment3,fragment4};
        PagerManager pagerManager = new PagerManager();
        pagerManager.addFragment(fragment0,indicators[0]);
        pagerManager.addFragment(fragment1,indicators[1]);
        pagerManager.addFragment(fragment2,indicators[2]);
        pagerManager.addFragment(fragment3,indicators[3]);
        pagerManager.addFragment(fragment4,indicators[4]);
        adapter = new ModelPagerAdapter(getSupportFragmentManager(), pagerManager);
        //将pagerManger放入到ViewPager中
        vp_content.setAdapter(adapter);
        //将ViewPager放入到SpringIndicator中
        indicator.setViewPager(vp_content);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        etSearch.setHint("请输入...");
                        break;
                    case 1:
                        etSearch.setHint("请输入...");
                        break;
                    case 2:
                        etSearch.setHint("请输入...");
                        break;
                    case 3:
                        etSearch.setHint("请输入...");
                        break;
                    case 4:
                        etSearch.setHint("请输入患者电话号码");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //跳转界面
    @Override
    public void switchPageUI(String className) {

    }

    //验证表单成功
    @Override
    public void onValidationSucceeded() {
        //拿到搜索的内容
        content = etSearch.getText().toString().trim();
        //去请求数据
        mSearchPresenter.clickSearchButton(content);


    }

    //验证表单失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        //弹出sweetDialog对话框,提示
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);

    }

    //拿到所有，医院，科室，医生的bean
    @Override
    public InitialiseSearchRequestBean getInitialiseSearchRequestBean() {
        //TODO
        index = vp_content.getCurrentItem();
        String type = keyWords[index];

        InitialiseSearchRequestBean initialiseSearchRequestBean = new InitialiseSearchRequestBeanBuilder().Build(type, content);
        return initialiseSearchRequestBean;
    }

    //获得界面索引
    @Override
    public int getIndex() {
        return index;
    }


    @OnClick({R.id.btnSearch, R.id.iv_delete, R.id.tvBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                //点击搜索按钮请求表单验证
                validator.validate();
                break;
            case R.id.iv_delete:
                //删除按钮进行清空
                etSearch.setText("");
                break;
            case R.id.tvBack:   //回退键
                initBackBtn(R.id.tvBack);   //BaseActivity 回退，结束当前页面
        }
    }


    @Override
    public String[] getKeyWordsList() {
        return keyWords;
    }


}
