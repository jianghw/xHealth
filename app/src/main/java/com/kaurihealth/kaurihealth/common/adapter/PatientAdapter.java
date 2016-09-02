package com.kaurihealth.kaurihealth.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.activity.MainActivity;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Interface.ISkip;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.Utils;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/4.
 * 备注：
 */
public class PatientAdapter extends CommonAdapter<PatientDisplayBean> {

    public static final int ReturnToMain = 3;

    public PatientAdapter(Context context, List<PatientDisplayBean> list) {
        super(context, list);
    }

    ISkip skip;

    public void setSkip(ISkip skip) {
        this.skip = skip;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.blankpatient_iteam, null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.setInfo(list.get(position));
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tv_blanname)
        TextView tv_blanname;
        @Bind(R.id.tv_gender)
        TextView tv_gender;
        @Bind(R.id.tv_time)
        TextView tv_time;
        private int patientId;
        IDoctorPatientRelationshipService doctorPatientRelationshipService;
        String accessToken;
        private IGetter getter;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


        @OnClick(R.id.button_insert)
        public void onClick() {
            getter = Getter.getInstance(context);
            accessToken = getter.getToken();
            doctorPatientRelationshipService = new ServiceFactory(Url.prefix ,context).getDoctorPatientRelationshipService();
            Call<DoctorPatientRelationshipBean> call = doctorPatientRelationshipService.InsertNewRelationshipByDoctor(patientId);
            call.enqueue(new Callback<DoctorPatientRelationshipBean>() {
                @Override
                public void onResponse(Call<DoctorPatientRelationshipBean> call, Response<DoctorPatientRelationshipBean> response) {
                    if (response.isSuccessful()) {
                        if (skip != null) {
                            skip.skip(MainActivity.class, null, ReturnToMain);
                        }
                    }
                }

                @Override
                public void onFailure(Call<DoctorPatientRelationshipBean> call, Throwable t) {
                    Toast.makeText(context, LoadingStatu.NetError.value, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setInfo(PatientDisplayBean patientDisplayBean) {
            patientId = patientDisplayBean.getPatientId();
            tv_blanname.setText(patientDisplayBean.getFullName());
            tv_gender.setText(patientDisplayBean.getGender());
            tv_time.setText(String.valueOf(Utils.calculateAge(patientDisplayBean.getDateOfBirth()) + "岁"));
        }
    }
}
