package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.SearchBooleanResultBean;
import com.kaurihealth.datalib.response_bean.SearchResultBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mip on 2016/12/15.
 */

public class DoctorSearchAdapter extends CommonAdapter<SearchBooleanResultBean> {


    public DoctorSearchAdapter(Context context, List<SearchBooleanResultBean> list) {
        super(context, list);
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.doctor_search_item, null);
            viewHolder = new ViewHolder(contentView);
            contentView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) contentView.getTag();
        }
        viewHolder.setInfo(list.get(position));
        return contentView;
    }

    class ViewHolder {
        @Bind(R.id.iv_avatar)
        CircleImageView iv_avatar;
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.tv_doctor_department)
        TextView tv_doctor_department;
        @Bind(R.id.tv_doctor_hospital)
        TextView tv_doctor_hospital;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(SearchBooleanResultBean bean) {
            SearchResultBean.ResultBean.ItemsBean itemsBean = bean.getItemsBean();
            if (bean.getItemsBean() != null) {
                ImageUrlUtils.picassoByUrlCircle(context, itemsBean.getAvatar(), iv_avatar);
                tv_name.setText(itemsBean.getFullName());
                tv_doctor_department.setText(itemsBean.getDepartmentName()!= null ? itemsBean.getDepartmentName() : "暂无");
                tv_doctor_hospital.setText(itemsBean.getHospitalTitle() != null ? itemsBean.getHospitalTitle() : "暂无");
            }
        }
    }
}
