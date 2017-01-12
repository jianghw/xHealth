package com.kaurihealth.kaurihealth.record_details_v.LikeToIosDialog;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.record_details_v.RecordDetailsActivity;

/**
 * Created by mip on 2016/12/22.
 */

public class PathologyDialog implements View.OnClickListener, PopupWindow.OnDismissListener {

    private PopupWindow popupWindow;
    private Activity mActivity;
    private static final String PATHOLOGY = "病理";

    public void openPopupWindow_item(Activity context, View v, PopupWindow popupWindow) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(context).inflate(R.layout.view_popupwindow_pathology, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 20);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick_v(view);
        //设置背景色
        setBackgroundAlpha(0.5f, context);
        this.popupWindow = popupWindow;
        mActivity = context;
    }

    TextView tv_cancel,clinical_outpatient;
    Button btn_1;
    ImageView clinical_back;

    private void setOnPopupViewClick_v(View view) {
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        clinical_outpatient = (TextView) view.findViewById(R.id.clinical_outpatient);
        clinical_back = (ImageView) view.findViewById(R.id.clinical_back);
        tv_cancel.setOnClickListener(this);
        clinical_back.setOnClickListener(this);
        clinical_outpatient.setOnClickListener(this);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        if (alpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                popupWindow.dismiss();
                break;
            case R.id.clinical_back:
                popupWindow.dismiss();
                listener.back();
                break;
            case R.id.clinical_outpatient:
                popupWindow.dismiss();
                ((RecordDetailsActivity)mActivity).getData(PATHOLOGY,clinical_outpatient.getText().toString());
                break;
        }
    }


    public void setListener(IBackToOpenListener listener){
        this.listener = listener;
    }

    private IBackToOpenListener listener;

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1,mActivity);
    }
//backToOpenListerner
    public interface IBackToOpenListener {
        void back();
    }
}
