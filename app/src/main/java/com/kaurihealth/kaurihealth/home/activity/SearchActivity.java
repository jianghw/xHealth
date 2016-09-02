package com.kaurihealth.kaurihealth.home.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.request_bean.bean.SearchDoctorDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.SearchResultBean;
import com.kaurihealth.datalib.request_bean.builder.InitialiseSearchRequestBeanBuilder;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.service.IPatientService;
import com.kaurihealth.datalib.service.ISearchService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.util.HandleData;
import com.kaurihealth.kaurihealth.home.adapter.ModelPagerAdapter;
import com.kaurihealth.kaurihealth.home.fragment.BlankAllFragment;
import com.kaurihealth.kaurihealth.home.fragment.BlankDoctorFragment;
import com.kaurihealth.kaurihealth.home.fragment.BlankHospitalFragment;
import com.kaurihealth.kaurihealth.home.fragment.BlankMedicineFragment;
import com.kaurihealth.kaurihealth.home.fragment.BlankPatientFragment;
import com.kaurihealth.kaurihealth.home.util.PagerManager;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Interface.ISkip;
import com.kaurihealth.kaurihealth.util.Url;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.youyou.zllibrary.util.CommonFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import github.chenupt.springindicator.SpringIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class SearchActivity extends CommonFragmentActivity implements Validator.ValidationListener {

    @Bind(R.id.iv_delete)
    ImageView ivDeleteText;

    @NotEmpty(message = "请输入您想搜索的内容！")
    @Bind(R.id.etSearch)
    EditText etSearch;
    private ModelPagerAdapter adapter;
    private ViewPager vp_content;
    private List<SearchDoctorDisplayBean> list;
    private Fragment[] fragments;
    private IPatientService patientService;
    private IGetter getter;
    private Validator validator;
    private ISearchService searchService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        setBack(R.id.tvBack);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btnSearch, R.id.iv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                validator.validate();
                break;
            case R.id.iv_delete:
                etSearch.setText("");
                break;
        }
    }


    public void init() {
        ivDeleteText.setVisibility(View.GONE);
        getter = Getter.getInstance(getApplicationContext());
        //表单验证器
        validator = new Validator(this);
        validator.setValidationListener(this);
        list = new ArrayList<>();
        // 初始化搜索
        patientService = new ServiceFactory(Url.prefix, getApplicationContext()).getPatientService();
        //搜索接口
        searchService = new ServiceFactory(Url.prefix, getApplicationContext()).getSearchService();
        ivDeleteText = (ImageView) findViewById(R.id.iv_delete);
        etSearch = (EditText) findViewById(R.id.etSearch);

        etSearch.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivDeleteText.setVisibility(View.GONE);
                } else {
                    ivDeleteText.setVisibility(View.VISIBLE);
                }
            }
        });
        //添加fragment
        SpringIndicator indicator = (SpringIndicator) findViewById(R.id.indicator);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        Fragment fragment0 = new BlankAllFragment();//所有
        Fragment fragment1 = new BlankDoctorFragment();//医生
        Fragment fragment2 = new BlankHospitalFragment();//医院
        Fragment fragment3 = new BlankMedicineFragment();//科室
        BlankPatientFragment fragment4 = new BlankPatientFragment();//患者
        fragment4.setSkip(skip);
        fragments = new Fragment[]{fragment0, fragment1, fragment2, fragment3, fragment4};
        PagerManager pagerManager = new PagerManager();
        pagerManager.addFragment(fragment0, indicators[0]);
        pagerManager.addFragment(fragment1, indicators[1]);
        pagerManager.addFragment(fragment2, indicators[2]);
        pagerManager.addFragment(fragment3, indicators[3]);
        pagerManager.addFragment(fragment4, indicators[4]);
        adapter = new ModelPagerAdapter(getSupportFragmentManager(), pagerManager);
        //将pagerManager放入到ViewPager中
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

    ISkip skip = new ISkip() {
        @Override
        public void skip(Class<? extends Activity> purpose, Bundle bundle, int requestCode) {
            setResult(requestCode);
            finishCur();
        }
    };
    String[] indicators = {"所有", "医生", "科室", "医院", "患者"};
    //用于关键字搜索
    String[] keyWords = {"default", "doctor", "hospital", "department", "patient"};

    private void search(String content) {
        final int selected = vp_content.getCurrentItem();
        String type = keyWords[selected];
        //搜寻患者
        if (type.equals("patient")) {
            Call<List<PatientDisplayBean>> listCall = patientService.SearchPatientByUserName_out(content);
            listCall.enqueue(new Callback<List<PatientDisplayBean>>() {
                @Override
                public void onResponse(Call<List<PatientDisplayBean>> call, Response<List<PatientDisplayBean>> response) {
                    if (response.isSuccessful()) {
                        List<PatientDisplayBean> patientDisplayBeenList = response.body();
                        //将数据添加到Fragment
                        ((BlankPatientFragment) fragments[4]).setData(patientDisplayBeenList);
                    } else {
                        showToast("获取数据失败");
                    }
                }

                @Override
                public void onFailure(Call<List<PatientDisplayBean>> call, Throwable t) {
                    t.printStackTrace();
                    showToast(LoadingStatu.NetError.value);
                }
            });
        } else {
            InitialiseSearchRequestBean initialiseSearchRequestBean = new InitialiseSearchRequestBeanBuilder().Build(type, content);    //type："default", "doctor", "hospital", "department", "patient"
            Call<SearchResultBean> call = searchService.KeywordSearch_out(initialiseSearchRequestBean);
            call.enqueue(new Callback<SearchResultBean>() {
                @Override
                public void onResponse(Call<SearchResultBean> call, Response<SearchResultBean> response) {
                    if (response.isSuccessful()) {
                        SearchResultBean searchResultBean = response.body();
                        if (searchResultBean.status.equals("OK")) {
                            List<SearchDoctorDisplayBean> data = searchResultBean.result.items;
                            ((HandleData<List<SearchDoctorDisplayBean>>) fragments[selected]).setData(data);
                            for (int i = 0; i < fragments.length; i++) {
                                if (i != selected) {
                                    ((HandleData<List<SearchDoctorDisplayBean>>) fragments[i]).clear();
                                }
                            }
                            showToast("查询数据成功！");
                        }
                    }
                }

                @Override
                public void onFailure(Call<SearchResultBean> call, Throwable t) {
                    for (int i = 0; i < fragments.length; i++) {
                        ((HandleData<List<SearchDoctorDisplayBean>>) fragments[i]).clear();
                    }
                    showToast(LoadingStatu.NetError.value);
                }
            });


        }
    }

    //表单验证通过（有搜索内容）
    @Override
    public void onValidationSucceeded() {
        //表单验证成功， 进行编辑框内容搜索
        String edtop = etSearch.getText().toString().trim();
        search(edtop);

    }

    //表单验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        StringBuilder prompt = new StringBuilder();
        for (ValidationError error : errors) {
            String message = error.getCollatedErrorMessage(SearchActivity.this);
            prompt.append(message);
        }
        new SweetAlertDialog(SearchActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText("错误提示").setContentText(prompt.toString()).show();
    }
}
