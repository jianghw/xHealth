package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBeanWrapper;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.util.List;

/**
 * Created by mip on 2016/12/13.
 */

public class DoctorContactsAdapterNew extends CommonAdapter<DoctorRelationshipBeanWrapper> {
    private List<DoctorRelationshipBeanWrapper> mBeanList;
    private Context context;
    MyItemClickListener mItemClickListener;

    public DoctorContactsAdapterNew(Context context, List<DoctorRelationshipBeanWrapper> mBeanList) {
        super(context, mBeanList);
        this.mBeanList = mBeanList;
        this.context = context;
    }

    /**
     * 给adapter设置数据 并刷新
     *
     * @param beanList
     */
    public void setmBeanList(List<DoctorRelationshipBeanWrapper> beanList) {
        if (mBeanList.size() > 0) mBeanList.clear();
        mBeanList.addAll(beanList);
    }


    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ContactsViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.doctor_item_new, null);
        }
        holder = new ContactsViewHolder(convertView);
        holder.setInfo(list.get(position),position);
        return convertView;
    }

    class ContactsViewHolder {
        public TextView tvIndex;
        public CircleImageView ivAvatar;
        public TextView tvName;
        public TextView tv_doctor_department;
        public TextView tv_doctor_hospital;

        public ContactsViewHolder(View itemView) {
            tvIndex = (TextView) itemView.findViewById(R.id.tv_index);
            ivAvatar = (CircleImageView) itemView.findViewById(R.id.iv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tv_doctor_department = (TextView) itemView.findViewById(R.id.tv_doctor_department);
            tv_doctor_hospital = (TextView) itemView.findViewById(R.id.tv_doctor_hospital);
        }

        public void setInfo(DoctorRelationshipBeanWrapper bean, int position) {
            if (bean.getName().getRelatedDoctor() != null) {
                if (position == 0 || !mBeanList.get(position - 1).getIndex().equals(bean.getIndex())) {
                    tvIndex.setVisibility(View.VISIBLE);
                    tvIndex.setText(bean.getIndex());
                } else {
                    tvIndex.setVisibility(View.GONE);
                }
                tvName.setText(bean.getName().getRelatedDoctor().getFullName());

                tv_doctor_department.setText(bean.getName().getRelatedDoctor().getDoctorInformations() != null ?
                        bean.getName().getRelatedDoctor().getDoctorInformations().getDepartment() != null ?
                                bean.getName().getRelatedDoctor().getDoctorInformations().getDepartment().getDepartmentName() : "暂无" : "暂无");

                tv_doctor_department.setText(bean.getName().getRelatedDoctor().getDoctorInformations() != null ?
                        bean.getName().getRelatedDoctor().getDoctorInformations().getDepartment() != null ?
                                bean.getName().getRelatedDoctor().getDoctorInformations().getDepartment().getDepartmentName() : "暂无" : "暂无");
                tv_doctor_hospital.setText(bean.getName().getRelatedDoctor().getDoctorInformations() != null ?
                        bean.getName().getRelatedDoctor().getDoctorInformations().getHospitalName() != null ?
                                bean.getName().getRelatedDoctor().getDoctorInformations().getHospitalName() : "暂无" : "暂无");
                ImageUrlUtils.picassoByUrlCircle(context, bean.getName().getRelatedDoctor().getAvatar(), ivAvatar);
            }
        }
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }
}
