package com.kaurihealth.kaurihealth.hospital_v;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.HospitalNamePresenter;
import com.kaurihealth.mvplib.patient_p.IHospitalNameView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/9/7.
 * <p/>
 * Describe:
 */
public class HospitalNameActivity extends BaseActivity implements IHospitalNameView {
    @Bind(R.id.edt_hospitalName)
    EditText edtHospitalName;
    @Bind(R.id.lv_content)
    ListView lvContent;

    private ArrayAdapter<String> adapter;
    private int requestcode;
    private String hospitalName;

    private List<String> listListView = new ArrayList();
    List<String> list = new ArrayList<>();

    @Inject
    HospitalNamePresenter<IHospitalNameView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_enter_hospital;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);
        Bundle data = getBundle();
        requestcode = data.getInt("requestcode");
        hospitalName = data.getString("hospitalName");
        edtHospitalName.setText(hospitalName);
        getAllHospital();
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.simple_list_item, listListView);
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener((parent, view, position, id) -> {
            String selectStr = listListView.get(position);
            edtHospitalName.setText(selectStr);
            final String hospitalName1 = edtHospitalName.getText().toString().trim();
            if (requestcode == 10) {
                Intent intent = getIntent();
                intent.putExtra("hospitalName", hospitalName1);
                setResult(RESULT_OK, intent);
            }
        });
    }

    @Override
    public void switchPageUI(String className) {

    }

    public void getAllHospital() {
        mPresenter.onSubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    /**
     * 得到所有的机构信息
     *
     * @param stringList
     */
    @Override
    public void getHospitalNameList(List<String> stringList) {
        if (list == null) {
            throw new IllegalStateException("list must be not null");
        }
        if (!list.isEmpty()) list.clear();
        list.addAll(stringList);
        setListView(list);
    }

    /**
     * 设置list view中的数据
     */
    public void setListView(List<String> data) {
        if (!listListView.isEmpty()) listListView.clear();
        listListView.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_operate)
    public void store() {
        final String hospitalName = edtHospitalName.getText().toString().trim();
        if (requestcode == 10) {
            Intent intent = getIntent();
            intent.putExtra("hospitalName", hospitalName);
            setResult(RESULT_OK, intent);
            finishCur();
        } else {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            bundle.putString("hospitalName", hospitalName);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finishCur();
        }
    }
}
