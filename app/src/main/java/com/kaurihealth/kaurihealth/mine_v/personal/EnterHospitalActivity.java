package com.kaurihealth.kaurihealth.mine_v.personal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorInformationDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.mine_p.EnterHospitalPresenter;
import com.kaurihealth.mvplib.mine_p.IEnterHospitalView;
import com.kaurihealth.utilslib.constant.Global;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
//edt_hospitalName

/**
 * 我的Fragment-->个人信息-->机构   created by Nick
 * 字段: DoctorDisplayBean-->DoctorInformationDisplayBean-->hospitalName
 */
public class EnterHospitalActivity extends BaseActivity implements IEnterHospitalView {

    @Inject
    EnterHospitalPresenter<IEnterHospitalView> mPresenter;
    @Bind(R.id.tv_more)
    TextView tvMore;
    @Bind(R.id.edt_hospitalName)
    EditText edtHospitalName;
    @Bind(R.id.ivDelete)
    ImageView ivDelete;
    @Bind(R.id.lv_content)
    ListView lvContent;

    private List<String> hospitalList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String bundleString;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_enter_hospital;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.hospital_title_tv_title));
        tvMore.setText(getString(R.string.title_save));

        bundleString = getBundle().getString(Global.Environment.BUNDLE);
        if (bundleString.equals(Global.Environment.UPDATE)) {
            //获取医院名称
            DoctorDisplayBean doctorDisplayBean = LocalData.getLocalData().getMyself();
            DoctorInformationDisplayBean doctorInformations = doctorDisplayBean.getDoctorInformations();
            if (doctorInformations != null)
                setEdtHospitalName(doctorInformations.getHospitalName());
            setSelection(edtHospitalName);
        } else {
            setEdtHospitalName(getBundle().getString("hospitalName"));
            setSelection(edtHospitalName);
        }

        //初始化Adapter
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.simple_list_item, hospitalList);

        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener((adapterView, view, position, id) -> {
            String selectHospital = hospitalList.get(position);
            setEdtHospitalName(selectHospital);

        });

        initAllHospitalList();
    }

    /**
     * 保存
     */
    @OnClick(R.id.tv_more)
    public void save() {
        if (bundleString.equals(Global.Environment.CHOICE)) {
            switchPageUI("");
        }else {
            mPresenter.onSubscribe();
        }
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
        String hospitalName1 = edtHospitalName.getText().toString().trim();
        intent.putExtra("hospitalName", hospitalName1);
        setResult(RESULT_OK, intent);
        finishCur();
    }

    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        DoctorDisplayBean bean = (DoctorDisplayBean) LocalData.getLocalData().getMyself().clone();
        DoctorInformationDisplayBean doctorInformations = bean.getDoctorInformations();
        if (doctorInformations != null) doctorInformations.setHospitalName(getEdtHospitalName());
        return bean;
    }

    //获得所有医院的信息
    @Override
    public void initAllHospitalList() {
        //Presenter 去调用底层数据
        mPresenter.getAllHospitalName();
    }

    //刷新ListView数据
    @Override
    public void refreshListView(List<String> data) {
        if (!hospitalList.isEmpty()) hospitalList.clear();
        hospitalList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    public String getEdtHospitalName() {
        return edtHospitalName.getText().toString().trim();
    }

    public void setEdtHospitalName(String edtHospitalName) {
        this.edtHospitalName.setText(edtHospitalName);
//        this.edtHospitalName.setSelection(edtHospitalName.length());  // 光标移到文本末尾
        setSelection(this.edtHospitalName);
    }
}
