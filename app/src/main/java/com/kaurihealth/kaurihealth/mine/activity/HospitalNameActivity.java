package com.kaurihealth.kaurihealth.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.activity.AddOutpatientRecordActivity;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class HospitalNameActivity extends CommonActivity {

    public static final int EdithHospitalName = 10;
    @Bind(R.id.edtHospitalName)
    EditText edtHospitalName;
    @Bind(R.id.lv_content)
    ListView lvContent;

    private LoadingUtil loadingUtil;
    private ArrayAdapter<String> adapter;
    private int requestcode;

    private List<String> listListView = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_hospital);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    String hospitalName;

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    List<String> list = new ArrayList<>();

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        Bundle data = getBundle();
        requestcode = data.getInt("requestcode");
        hospitalName = data.getString("hospitalName");
        edtHospitalName.setText(hospitalName);
        getAllHospital();
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_list_iteam, listListView);
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectStr = listListView.get(position);
                edtHospitalName.setText(selectStr);
                final String hospitalName = edtHospitalName.getText().toString().trim();
                if (requestcode == AddOutpatientRecordActivity.EdithHospitalName) {
                    Intent intent = getIntent();
                    intent.putExtra("hospitalName", hospitalName);
                    setResult(RESULT_OK, intent);
                }
            }
        });
    }

    /**
     * 获得所有的医院信息
     */
    private void getAllHospital() {
        loadingUtil = LoadingUtil.getInstance(this);
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix,getApplicationContext());
        serviceFactory.getLoadAllHospitalsService()
                .LoadAllHospitals()
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {
                        if (response.isSuccessful()) {
                            list.clear();
                            list.addAll(response.body());
                            setListView(list);
                            loadingUtil.dismiss();
                        } else {
                            loadingUtil.dismiss("获取数据失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        loadingUtil.dismiss(LoadingStatu.NetError.value);
                    }
                });
        loadingUtil.show();
    }



    /**
     * 设置listview中的数据
     */
    private void setListView(List<String> data) {
        listListView.clear();
        listListView.addAll(data);
        adapter.notifyDataSetChanged();
    }


    @OnClick(R.id.tv_operate)
    public void store() {
        final String hospitalName = edtHospitalName.getText().toString().trim();
        if (requestcode == AddOutpatientRecordActivity.EdithHospitalName) {
            Intent intent = getIntent();
            intent.putExtra("hospitalName", hospitalName);
            setResult(RESULT_OK, intent);
            loadingUtil.show();
            loadingUtil.dismiss("修改成功", new LoadingUtil.Success() {
                @Override
                public void dismiss() {
                    finishCur();
                }
            });
        } else {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            bundle.putString("hospitalName", hospitalName);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            loadingUtil.show();
            loadingUtil.dismiss("修改成功", new LoadingUtil.Success() {
                @Override
                public void dismiss() {
                    finishCur();
                }
            });
        }
    }
}
