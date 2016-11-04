package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestReferralPatientDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mip on 2016/10/26.
 */

public class ReferralPatientAdapter extends CommonAdapter<DoctorPatientRelationshipBean> {
    private List<PatientRequestReferralPatientDisplayBean.PatientListReferralBean> referralBeen;

    public ReferralPatientAdapter(Context context,
                                  List<DoctorPatientRelationshipBean> list,
                                  List<PatientRequestReferralPatientDisplayBean.PatientListReferralBean> referralBeen) {
        super(context, list);
        this.referralBeen = referralBeen;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.referral_patient_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.setInfo(list.get(position), position);
        return convertView;
    }

    class ViewHolder {

        @Bind(R.id.civPhoto)
        CircleImageView civPhoto;
        @Bind(R.id.tv_referral_request_name)
        TextView tv_referral_request_name;
        @Bind(R.id.tv_referral_patient_gender)
        TextView tv_referral_patient_gender;
        @Bind(R.id.tv_referral_patient_age)
        TextView tv_referral_patient_age;
        @Bind(R.id.tv_referral_patient_type)
        TextView tv_referral_patient_type;
        @Bind(R.id.tv_referral_checkbox)
        CheckBox tv_referral_checkbox;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(DoctorPatientRelationshipBean bean, int position) {

            if (bean.getPatient() != null) {
                PatientDisplayBean patient = bean.getPatient();
                if (CheckUtils.checkUrlNotNull(patient.getAvatar())) {
                    ImageUrlUtils.picassoBySmallUrlCircle(context, patient.getAvatar(), civPhoto);
                } else {
                    civPhoto.setImageResource(R.mipmap.ic_circle_head_green);
                }
                tv_referral_patient_gender.setText(patient.getGender());
                tv_referral_patient_age.setText(DateUtils.calculateAge(patient.getDateOfBirth()) + "");
                tv_referral_patient_type.setText(bean.getModifyRelationshipReason());
                tv_referral_request_name.setText(patient.getFullName());
                tv_referral_checkbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                    PatientRequestReferralPatientDisplayBean.PatientListReferralBean listReferralBean = null;
                    if (!isChecked) {
                        bean.type = 0;
                    }
                    if (isChecked) {
                        bean.type = 1;//表示被点击
                        listReferralBean =
                                new PatientRequestReferralPatientDisplayBean.PatientListReferralBean();
                        listReferralBean.setPatientId(bean.getPatientId());
                        listReferralBean.setDoctorPatientShipId(bean.getDoctorPatientId());

                        if (!(referralBeen.contains(listReferralBean))) {
                            referralBeen.add(listReferralBean);
                        }
                    } else if (referralBeen.contains(listReferralBean)) {
                        referralBeen.remove(referralBeen.indexOf(listReferralBean));
                    }
                });
                //防止checkBox混乱的问题
                if (bean.type == 1) {
                    tv_referral_checkbox.setChecked(true);
                } else {
                    tv_referral_checkbox.setChecked(false);
                }
            }

        }
    }
}
