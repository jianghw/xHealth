package com.kaurihealth.kaurihealth.patient_v.prescription;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class PrescriptionImageActivity extends BaseActivity {

    @Bind(R.id.imageView)
    ImageView imageView;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.prescriptionlmage_in_fo;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String ImageUrl = bundle.getString("ImageUrl");

        Picasso.with(this)
                .load(ImageUrl)
                .skipMemoryCache()
                .fit()
                .into(imageView);
    }

    @Override
    protected void initDelayedData() {
    }

}
