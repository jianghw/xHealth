package com.kaurihealth.kaurihealth.patientwithdoctor.bean;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.HealthConditionDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface.IHealthyRecordHabit;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Mode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/5/20.
 * 介绍：月经史
 */
public class HealthyRecordNewMenstrualReproductive {
    private final int Length = 7;
    private String[] arr = {"月经周期", "月经量", "怀孕", "生育", "流产", "是否有宫外孕", "是否有剖腹产"};
    //月经周期
    @Bind(R.id.spPeriod)
    TextView tvPeriod;
    //月经量
    @Bind(R.id.spAmount)
    TextView tvAmount;
    //怀孕次数
    @Bind(R.id.spPregnancyNum)
    TextView tvPregnancyNum;
    //生育次数
    @Bind(R.id.spGetBirthNum)
    TextView tvGetBirthNum;
    //流产次数
    @Bind(R.id.spMisbirth)
    TextView tvMisbirth;
    //是否有宫外孕
    @Bind(R.id.spEctopicPregnancy)
    TextView tvEctopicPregnancy;
    //是否有刨腹产
    @Bind(R.id.spCesarean)
    TextView tvCesarean;
    private String category = "月经与生育史";
    private int patientId;
    private Mode mode;
    private Activity activity;
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private final LayoutInflater from;
    private View view;
    private Resources resources;
    private String[] Amount = new String[10];
    private Data[] data;

    {
        layoutParams.setMargins(10, 0, 10, 60);
        for (int i = 0; i < 10; i++) {
            Amount[i] = String.format("%d次", i);
        }
    }

    public HealthyRecordNewMenstrualReproductive(int patientId, Mode mode, Activity activity) {
        this.patientId = patientId;
        this.mode = mode;
        this.activity = activity;
        resources = activity.getResources();
        from = LayoutInflater.from(activity.getApplicationContext());
        init();
    }

    private void init() {
        view = from.inflate(R.layout.healthyrecordmenstruationiteam, null);
        view.setLayoutParams(layoutParams);
        ButterKnife.bind(this, view);
        String[] menstruationPeriods = resources.getStringArray(R.array.menstruationPeriod);
        String[] menstruationAmounts = resources.getStringArray(R.array.menstruationAmount);
        String[] booleanStrs = resources.getStringArray(R.array.booleanStr);
        data = new Data[Length];
        boolean isTouchAble;
        if (mode == mode.EditAndRead) {
            isTouchAble = true;
        } else {
            isTouchAble = false;
        }
        data[0] = new Data(arr[0], menstruationPeriods, tvPeriod, activity, isTouchAble, patientId, category);
        data[1] = new Data(arr[1], menstruationAmounts, tvAmount, activity, isTouchAble, patientId, category);
        data[2] = new Data(arr[2], Amount, tvPregnancyNum, activity, isTouchAble, patientId, category);
        data[3] = new Data(arr[3], Amount, tvGetBirthNum, activity, isTouchAble, patientId, category);
        data[4] = new Data(arr[4], Amount, tvMisbirth, activity, isTouchAble, patientId, category);
        data[5] = new Data(arr[5], booleanStrs, tvEctopicPregnancy, activity, isTouchAble, patientId, category);
        data[6] = new Data(arr[6], booleanStrs, tvCesarean, activity, isTouchAble, patientId, category);
    }


    public IHealthyRecordHabit<HealthConditionDisplayBean> getHealthyRecordHabitInterface() {
        return new IHealthyRecordHabit<HealthConditionDisplayBean>() {
            @Override
            public boolean isThisType(HealthConditionDisplayBean healthySmallIteamBean) {
                return healthySmallIteamBean.category.equals(category);
            }

            @Override
            public void add(HealthConditionDisplayBean healthySmallIteamBean) {
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals(healthySmallIteamBean.type)) {
                        data[i].setData(healthySmallIteamBean);
                        break;
                    }
                }
            }

            @Override
            public List<HealthConditionDisplayBean> getAll() {
                List<HealthConditionDisplayBean> iteams = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    if (data[i] != null) {
                        iteams.add(data[i].getData());
                    }
                }
                return iteams;
            }

            @Override
            public View getView() {
                return view;
            }
        };
    }

}
