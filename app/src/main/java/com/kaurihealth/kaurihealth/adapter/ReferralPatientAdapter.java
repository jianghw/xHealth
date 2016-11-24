package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mip on 2016/10/26.
 */

public class ReferralPatientAdapter extends CommonAdapter<DoctorPatientRelationshipBean> {
    private List<PatientRequestReferralPatientDisplayBean.PatientListReferralBean> referralBeen;
    private List<Integer> integers = new ArrayList<>();
    private ArrayMap<Integer, PatientRequestReferralPatientDisplayBean.PatientListReferralBean> map = new ArrayMap<>();
//    PatientRequestReferralPatientDisplayBean.PatientListReferralBean listReferralBean = new PatientRequestReferralPatientDisplayBean.PatientListReferralBean();

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

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    public void myNotifyDataSetChanged() {
        if (!map.isEmpty()) map.clear();
        notifyDataSetChanged();
    }

    public void notifyNoDataChanged() {
        if (!map.isEmpty()) map.clear();
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

        public void setInfo(DoctorPatientRelationshipBean relationshipBean, int position) {
            PatientDisplayBean patient = relationshipBean.getPatient();
            if (patient != null) {
                if (CheckUtils.checkUrlNotNull(patient.getAvatar())) {
                    ImageUrlUtils.picassoBySmallUrlCircle(context, patient.getAvatar(), civPhoto);
                } else {
                    civPhoto.setImageResource(R.mipmap.ic_circle_head_green);
                }
                tv_referral_patient_gender.setText(patient.getGender());
                tv_referral_patient_age.setText(DateUtils.calculateAge(patient.getDateOfBirth()) + "");
                tv_referral_patient_type.setText(relationshipBean.getModifyRelationshipReason());
                tv_referral_request_name.setText(patient.getFullName());
                //防止checkBox混乱的问题
                if (relationshipBean.type == 1) {
                    tv_referral_checkbox.setChecked(true);
                } else {
                    tv_referral_checkbox.setChecked(false);
                }

                tv_referral_checkbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                    if (!isChecked) {
                        relationshipBean.type = 0;
                        if (map.containsKey(relationshipBean.getPatientId())) {
                            referralBeen.remove(map.get(relationshipBean.getPatientId()));
                            map.remove(relationshipBean.getPatientId());
                        }
                    } else {
                        relationshipBean.type = 1;//表示被点击
                        PatientRequestReferralPatientDisplayBean.PatientListReferralBean listReferralBean =
                                new PatientRequestReferralPatientDisplayBean.PatientListReferralBean();
                        listReferralBean.setPatientId(relationshipBean.getPatientId());
                        listReferralBean.setDoctorPatientShipId(relationshipBean.getDoctorPatientId());
                        if (!map.containsKey(relationshipBean.getPatientId())) {
                            map.put(listReferralBean.getPatientId(), listReferralBean);
                            referralBeen.add(listReferralBean);
                        }
                    }
                });
            }
        }
    }


}
