package com.kaurihealth.kaurihealth.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;

/**
 * Created by 米平 on 2016/7/27.
 */
public class TextWatchClearChange implements TextWatcher {
    private EditText editText;
    private ImageView imageView;
    private String avatar;
    private Context context;
    private IGetter getter;



    public TextWatchClearChange(EditText editText, ImageView imageView, IGetter getter, Context context) {
        this.editText = editText;
        this.imageView =imageView;
        this.getter = getter;
        this.context = context;
        avatar =getter.getAvatar();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        imageView.setImageResource(R.mipmap.ic_login_head_default);
        if (editText.getText().toString().equals(getter.getAccount())){
            if (!(TextUtils.isEmpty((avatar)))) {
                PicassoImageLoadUtil.loadUrlToImageView(avatar, imageView, context);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (editText.getText().toString().equals(getter.getAccount())){
            if (!(TextUtils.isEmpty((avatar)))) {
                PicassoImageLoadUtil.loadUrlToImageView(avatar, imageView, context);
            }
        }
    }
}
