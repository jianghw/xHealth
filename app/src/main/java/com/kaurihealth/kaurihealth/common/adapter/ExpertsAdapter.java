package com.kaurihealth.kaurihealth.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.commonlibrary.widget.CircleImageView;
import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 版权所有者：刘正
 * 创建日期： 2016/3/24.
 * 备注：
 */
public class ExpertsAdapter extends CommonAdapter<DoctorPatientRelationshipBean> {

    private ImageLoader imageLoader;

    public ExpertsAdapter(Context context, List<DoctorPatientRelationshipBean> list) {
        super(context, list);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.experts_iteam, null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.setData(list.get(position));
        return convertView;
    }



    class ViewHolder {
        @Bind(R.id.civ_photo)
        CircleImageView civPhoto;
        @Bind(R.id.tv_name)
        TextView tvName;

        @Bind(R.id.tv_hospitalName)
        TextView tvhospitalName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(DoctorPatientRelationshipBean doctorPatientRelationshipBean) {

            imageLoader.displayImage(doctorPatientRelationshipBean.doctor.avatar, civPhoto);

            tvName.setText(doctorPatientRelationshipBean.doctor.fullName);

            tvhospitalName.setText(doctorPatientRelationshipBean.doctor.doctorInformations.hospitalName);

            civPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //skipToIteam(data);
                }
            });
        }
    }
}
