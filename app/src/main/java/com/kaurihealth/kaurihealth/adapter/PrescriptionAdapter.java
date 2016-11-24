package com.kaurihealth.kaurihealth.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.patient_v.prescription.PrescriptionImageActivity;
import com.kaurihealth.utilslib.TranslationAnim;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.GalleryUtil;
import com.kaurihealth.utilslib.image.ImagSizeMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class PrescriptionAdapter extends CommonAdapter<PrescriptionBean> {

    ImagSizeMode.ImageSizeBean imagSizeBean=ImagSizeMode.imageSizeBeens[2];
    private Activity activity;

    public PrescriptionAdapter(Activity activity, List<PrescriptionBean> list) {
        super(activity.getApplicationContext(), list);
        this.activity = activity;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.prescription_iteam, null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView, position);
        viewHolder.setInfo(list.get(position));
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    class ViewHolder {

        @Bind(R.id.iv_photo_homeiteam)
        ImageView ivPhotoHomeiteam;

        @Bind(R.id.iv_photo_homeiteam2)
        ImageView ivPhotoHomeiteam2;

        @Bind(R.id.tv_time_homitam)
        TextView tvTimeHomitam;

        @Bind(R.id.tv_type)
        TextView tv_type;
        int position;

        public ViewHolder(View view, int position) {
            ButterKnife.bind(this, view);
            this.position = position;
            init();
        }

        private void init() {
            ivPhotoHomeiteam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (list.get(position).getPrescriptionDocuments() != null) {
                        skipToIteam(list.get(position).getPrescriptionDocuments().get(0).getdocumentUrl());
                    }
                }
            });
        }

        /**
         * 点击listview中的某一个子项，跳转到该子项代表的处方中
         */
        private void skipToIteam(String ImageUrl) {
            Bundle bundle = new Bundle();
            bundle.putString("ImageUrl", ImageUrl);
            TranslationAnim.zlStartActivityForResult(activity, PrescriptionImageActivity.class, bundle, 1);
        }

        public void setInfo(PrescriptionBean patient) {
            final List<PrescriptionBean.PrescriptionDocumentsEntity> ListPI = patient.getPrescriptionDocuments();
            if (ListPI == null) {
                ivPhotoHomeiteam.setVisibility(View.INVISIBLE);
                ivPhotoHomeiteam2.setVisibility(View.INVISIBLE);
            } else {
//                ivPhotoHomeiteam.setVisibility(View.VISIBLE);
//                ivPhotoHomeiteam2.setVisibility(View.VISIBLE);
                if (ListPI.size() == 1) {
                    ivPhotoHomeiteam.setVisibility(View.VISIBLE);
//                    ivPhotoHomeiteam2.setVisibility(View.INVISIBLE);
//                    ImageUrlUtils.picassoBySmallUrlCircle(context,ListPI.get(0).getdocumentUrl(),ivPhotoHomeiteam);
                } else if (ListPI.size() >= 2) {
                    ivPhotoHomeiteam2.setVisibility(View.VISIBLE);
//                    ImageUrlUtils.picassoBySmallUrlCircle(context,ListPI.get(0).getdocumentUrl(),ivPhotoHomeiteam);
//                    ImageUrlUtils.picassoBySmallUrlCircle(context,ListPI.get(1).getdocumentUrl(),ivPhotoHomeiteam2);
                }
            }
            tv_type.setText(patient.getDoctor());
            tvTimeHomitam.setText(DateUtils.getFormatDate(patient.getDate()));
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GalleryUtil.toGallery(activity, new GalleryUtil.GetUrlsInterface() {
                        @Override
                        public ArrayList<String> getUrls() {
                            if (listImage.size() == 0) {
                                for (int i = 0; i < ListPI.size(); i++) {
                                    listImage.add(ListPI.get(i).getdocumentUrl());
                                }
                            }
                            return listImage;
                        }
                    });
                }
            };
            ivPhotoHomeiteam.setOnClickListener(onClickListener);
            ivPhotoHomeiteam2.setOnClickListener(onClickListener);
        }

        ArrayList<String> listImage = new ArrayList<>();
    }

}
