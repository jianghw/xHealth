package com.kaurihealth.kaurihealth.record_details_v.LikeToIosDialog;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.record_details_v.RecordDetailsActivity;

/**
 * Created by mip on 2016/12/22.
 */

public class LobTestDialog implements View.OnClickListener, PopupWindow.OnDismissListener   {
    private PopupWindow popupWindow;
    private Activity mActivity;
    private static final String LOBTEST = "实验室检查";

    public void openPopupWindow_item(Activity context, View v, PopupWindow popupWindow) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(context).inflate(R.layout.view_popupwindow_lobtest, null);
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

    TextView tv_cancel,clinical_outpatient,lobtest_urine,
            lobtest_faeces,lobtest_special,lobtest_other;
    ImageView clinical_back;

    private void setOnPopupViewClick_v(View view) {
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        clinical_outpatient = (TextView) view.findViewById(R.id.clinical_outpatient);
        lobtest_urine = (TextView) view.findViewById(R.id.lobtest_urine);
        lobtest_faeces = (TextView) view.findViewById(R.id.lobtest_faeces);
        lobtest_special = (TextView) view.findViewById(R.id.lobtest_special);
        lobtest_other = (TextView) view.findViewById(R.id.lobtest_other);
        clinical_back = (ImageView) view.findViewById(R.id.clinical_back);
        tv_cancel.setOnClickListener(this);
        clinical_back.setOnClickListener(this);
        clinical_outpatient.setOnClickListener(this);
        lobtest_urine.setOnClickListener(this);
        lobtest_faeces.setOnClickListener(this);
        lobtest_special.setOnClickListener(this);
        lobtest_other.setOnClickListener(this);
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
                IBackToOpenListener.back();
                break;
            case R.id.clinical_outpatient:
                popupWindow.dismiss();
                ((RecordDetailsActivity)mActivity).getData(LOBTEST,clinical_outpatient.getText().toString());
                break;
            case R.id.lobtest_urine:
                popupWindow.dismiss();
                ((RecordDetailsActivity)mActivity).getData(LOBTEST,lobtest_urine.getText().toString());
                break;
            case R.id.lobtest_faeces:
                popupWindow.dismiss();
                ((RecordDetailsActivity)mActivity).getData(LOBTEST,lobtest_faeces.getText().toString());
                break;
            case R.id.lobtest_special:
                popupWindow.dismiss();
                ((RecordDetailsActivity)mActivity).getData(LOBTEST,lobtest_special.getText().toString());
                break;
            case R.id.lobtest_other:
                popupWindow.dismiss();
                ((RecordDetailsActivity)mActivity).getData(LOBTEST,lobtest_other.getText().toString());
                break;
        }
    }


    public void setIBackToOpenListener(IBackToOpenListener IBackToOpenListener){
        this.IBackToOpenListener = IBackToOpenListener;
    }

    private IBackToOpenListener IBackToOpenListener;

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1,mActivity);
    }

    public interface IBackToOpenListener {
        void back();
    }
}
