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

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/4/28.
 * 介绍：
 */
public class HealthyRecordHabitNewBean {
    @Bind(R.id.spSmoke)
    TextView tvSmoke;
    @Bind(R.id.spDrink)
    TextView tvDrink;
    @Bind(R.id.spExerciseHabit)
    TextView tvExerciseHabit;
    @Bind(R.id.spDiet)
    TextView tvDiet;
    private final String category = "个人生活习惯";
    private Activity activity;
    private final LayoutInflater from;
    private String[] smokeStrs;
    private String[] drinkStrs;
    private String[] exerciseStrs;
    private String[] dietStrs;
    private final int CannotFind = -1;
    private int patientId;
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private Data[] datas;

    {
        layoutParams.setMargins(10, 10, 10, 40);
    }

    private Mode mode;

    public HealthyRecordHabitNewBean(Activity activity, int patientId, Mode mode) {
        this.mode = mode;
        this.activity = activity;
        this.patientId = patientId;
        from = LayoutInflater.from(activity.getApplicationContext());
        init();
    }

    View view;

    private void init() {
        view = from.inflate(R.layout.healthyrecordhabititeam, null);
        view.setLayoutParams(layoutParams);
        ButterKnife.bind(this, view);
        Resources resources = activity.getResources();
        smokeStrs = resources.getStringArray(R.array.smokeslect);
        drinkStrs = resources.getStringArray(R.array.drinkLevel);
        exerciseStrs = resources.getStringArray(R.array.exerciseHabit);
        dietStrs = resources.getStringArray(R.array.Diet);
        boolean touchAble;
        if (mode == Mode.EditAndRead) {
            touchAble = true;
        } else {
            touchAble = false;
        }
        datas = new Data[]{new Data("吸烟", smokeStrs, tvSmoke, activity, touchAble, patientId, category),
                new Data("饮酒", drinkStrs, tvDrink, activity, touchAble, patientId, category),
                new Data("运动习惯", exerciseStrs, tvExerciseHabit, activity, touchAble, patientId, category),
                new Data("饮食习惯", dietStrs, tvDiet, activity, touchAble, patientId, category)};

    }
    public IHealthyRecordHabit<HealthConditionDisplayBean> getHealthyRecordHabitInterface() {
        IHealthyRecordHabit<HealthConditionDisplayBean> healthyRecordHabitInterface = new IHealthyRecordHabit<HealthConditionDisplayBean>() {
            @Override
            public boolean isThisType(HealthConditionDisplayBean healthySmallIteamBean) {
                if (category.equals(healthySmallIteamBean.category)) {
                    return true;
                } else {
                    return false;
                }
            }
            @Override
            public void add(HealthConditionDisplayBean healthySmallIteamBean) {
                switch (healthySmallIteamBean.type) {
                    case "吸烟":
                        datas[0].setData(healthySmallIteamBean);
                        break;
                    case "饮酒":
                        datas[1].setData(healthySmallIteamBean);
                        break;
                    case "运动习惯":
                        datas[2].setData(healthySmallIteamBean);
                        break;
                    case "饮食习惯":
                        datas[3].setData(healthySmallIteamBean);
                        break;
                }
            }

            @Override
            public List<HealthConditionDisplayBean> getAll() {
                List<HealthConditionDisplayBean> list = new LinkedList<>();
                for (int i = 0; i < datas.length; i++) {
                    if (datas[i].getData() != null) {
                        list.add(datas[i].getData());
                    }
                }
                return list;
            }

            @Override
            public View getView() {
                return view;
            }
        };
        return healthyRecordHabitInterface;
    }
}
