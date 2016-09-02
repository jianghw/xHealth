package com.kaurihealth.kaurihealth.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youyou.zllibrary.util.CommonActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class PrescriptionImageActivity extends CommonActivity {

    private String ImageUrl;

    private ImageLoader imageLoader;

    @Bind(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescriptionlmage_in_fo);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    public void init() {
        super.init();
        //创建默认的ImageLoader配置参数
        imageLoader = ImageLoader.getInstance();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ImageUrl = bundle.getString("ImageUrl");
        imageLoader.displayImage(ImageUrl,imageView);
    }

}
