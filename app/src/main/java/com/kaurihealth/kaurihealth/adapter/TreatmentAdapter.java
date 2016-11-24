package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.DateConvertUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/23.
 * 备注：疾病与治疗Adapter
 */
public class TreatmentAdapter extends CommonAdapter {

    public TreatmentAdapter(Context context, List<MedicalLiteratureDisPlayBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_clinical, null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.setInfo((MedicalLiteratureDisPlayBean) list.get(position));
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    class ViewHolder {
        @Bind(R.id.iv_titleImage)
        ImageView ivTitleImage;
        @Bind(R.id.tv_contentBrief)
        TextView tvContentBrief;
        @Bind(R.id.tv_creatTime)
        TextView tvCreatTime;
        @Bind(R.id.tv_browse)
        TextView tvBrowse;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean) {
            if (medicalLiteratureDisPlayBean.titleImage != null) {
                ImageUrlUtils.picassoBySmallUrlReference(context, medicalLiteratureDisPlayBean.titleImage, ivTitleImage);
            }
            tvContentBrief.setText(medicalLiteratureDisPlayBean.medicalLiteratureTitle);
            tvCreatTime.setText(DateConvertUtils.getWeekOfDate(null,medicalLiteratureDisPlayBean.creatTime));
            tvBrowse.setText(medicalLiteratureDisPlayBean.browse + "阅读");
        }
    }
}
