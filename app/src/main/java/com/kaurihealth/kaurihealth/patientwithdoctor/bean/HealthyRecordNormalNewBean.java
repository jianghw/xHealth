package com.kaurihealth.kaurihealth.patientwithdoctor.bean;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.HealthConditionDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.HealthyRecordInterface;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface.IHealthyRecordNewActivity;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Mode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张磊 on 2016/4/27.
 * 介绍：
 */
public class HealthyRecordNormalNewBean {
    @Bind(R.id.tvAge)
    TextView tvTitle;
    @Bind(R.id.ivAddCurrentHealthyProblem)
    ImageView ivAddCurrentHealthyProblem;
    @Bind(R.id.layContainer)
    LinearLayout layContainer;
    private String category;
    ViewGroup parent;
    Context context;
    List<HealthConditionDisplayBean> list = new LinkedList<>();
    private IHealthyRecordNewActivity healthyRecordNewActivityController;
    private int id;
    private final int CannotSearch = -1;
    List<HealthConditionDisplayBean> listRes;
    public int patientID;
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    {
        layoutParams.setMargins(10, 10, 10, 20);
    }

    private Mode mode;

    public HealthyRecordNormalNewBean(int id, Context context, String category, IHealthyRecordNewActivity healthyRecordNewActivityController, int patientID, Mode mode) {
        this.context = context;
        this.mode = mode;
        this.category = category;
        this.id = id;
        this.patientID = patientID;
        this.healthyRecordNewActivityController = healthyRecordNewActivityController;
        init();
    }

    private void init() {
        parent = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.healthyrecordnewiteam, null);
        parent.setLayoutParams(layoutParams);
        ButterKnife.bind(this, parent);
        tvTitle.setText(category);
        if (mode == Mode.ReadOnly) {
            ivAddCurrentHealthyProblem.setVisibility(View.GONE);
        }

    }


    public ViewGroup getParent() {
        return parent;
    }

    public HealthyRecordInterface<HealthConditionDisplayBean> getHealthyRecordInterface() {
        HealthyRecordInterface<HealthConditionDisplayBean> control = new HealthyRecordInterface<HealthConditionDisplayBean>() {
            @Override
            public void add(HealthConditionDisplayBean healthySmallIteamBean) {
                if (layContainer.getChildCount() == 1 && list.size() == 0) {
                    layContainer.removeViewAt(0);
                    addView(healthySmallIteamBean);
                } else {
                    addView(healthySmallIteamBean);
                }
            }

            private void addView(HealthConditionDisplayBean healthySmallIteamBean) {
                list.add(healthySmallIteamBean);
                final View child = LayoutInflater.from(context).inflate(R.layout.heathyrecorditeam, null);
                layContainer.addView(child);
                //去掉其自布局的分割线
                layContainer.getChildAt(0).findViewById(R.id.line_item).setVisibility(View.GONE);

                final LinearLayout layBtnContainer = (LinearLayout) child.findViewById(R.id.layBtnContainer);
                if (mode == Mode.EditAndRead) {
                    log("打开了开关");
                    child.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (layBtnContainer.getVisibility() == View.VISIBLE) {
                                layBtnContainer.setVisibility(View.GONE);
                            } else {
                                layBtnContainer.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } else {
                    log("关闭了开关");
                }
                //编辑
                TextView tvEdit = (TextView) child.findViewById(R.id.tvEdit);
                //删除
                TextView tvDelete = (TextView) child.findViewById(R.id.tvDelete);
                TextView tvOrder = (TextView) child.findViewById(R.id.tvOrder);
                TextView tvOrder01 = (TextView) child.findViewById(R.id.tvOrder01);
                tvOrder01.setText(list.size() + "");
                //健康记录
                TextView tvDiseaseName = (TextView) child.findViewById(R.id.tvDiseaseName);


                tvDiseaseName.setText(healthySmallIteamBean.detail);

//                //去掉健康记录最后一个分割线
                if (list.size() == list.size()) {
                    tvOrder.setVisibility(View.GONE);

                }

                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getPositionInparent(child);
                        if (position != CannotSearch) {
                            healthyRecordNewActivityController.edit(id, position, category);
                            layBtnContainer.setVisibility(View.GONE);
                        }
                    }
                });
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteViewFromParent(child);
                    }
                });
            }

            @Override
            public void edit(int index, String value) {
                list.get(index).detail = value;
                TextView tvDiseaseName = (TextView) layContainer.getChildAt(index).findViewById(R.id.tvDiseaseName);
                tvDiseaseName.setText(value);
            }

            @Override
            public List<HealthConditionDisplayBean> getAll() {
                return list;
            }

            @Override
            public boolean isThisType(HealthConditionDisplayBean healthySmallIteamBean) {
                return healthySmallIteamBean.category.equals(category);
            }

            @Override
            public String getType() {
                return category;
            }

            @Override
            public void addNewOne(String value) {
//                new HealthySmallIteamBean(0, patientId, category, valueAdd, map.get(idAdd).getType());
                HealthConditionDisplayBean healthySmallIteamBean = new HealthConditionDisplayBean(0, patientID, category, value, category);
                add(healthySmallIteamBean);
            }

            @Override
            /**
             * 返回需要删除的选项，返回null,表示不需要删除
             *
             * @return
             */
            public List<HealthConditionDisplayBean> getListNeedToDelete() {
                Iterator<HealthConditionDisplayBean> iterator = listNeedToDelete.iterator();
                while (iterator.hasNext()) {
                    HealthConditionDisplayBean next = iterator.next();
                    if (next.healthConditionId == 0) {
                        iterator.remove();
                    }
                }
                return listNeedToDelete;
            }

            @Override
            public void setMode(Mode mode) {
                switch (mode) {
                    case ReadOnly:
                        ivAddCurrentHealthyProblem.setClickable(false);
                        break;
                    case EditAndRead:
                        ivAddCurrentHealthyProblem.setClickable(true);
                        break;
                }
            }

            /**
             * 特别需要小心的一个方法，一旦刚开始的初始化结束之后，必须得调用这个方法
             */
            public void setListRes() {
                listRes = (LinkedList<HealthConditionDisplayBean>) (((LinkedList<HealthConditionDisplayBean>) list).clone());
            }

            @Override
            public HealthConditionDisplayBean getValueAt(int position) {
                return list.get(position);
            }
        };
        return control;
    }

    /**
     * 删除某个项之后，重新排版顺序
     */
    private void resetOrder() {
        int count = layContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            TextView tvOrder01 = (TextView) layContainer.getChildAt(i).findViewById(R.id.tvOrder01);
            tvOrder01.setText((i + 1) + "");
        }
    }

    List<HealthConditionDisplayBean> listNeedToDelete = new LinkedList<>();

    /**
     * 将当前view从父布局中删除
     */
    private void deleteViewFromParent(View view) {
        int position = getPositionInparent(view);
        if (position != CannotSearch) {
            ((ViewGroup) (view.getParent())).removeViewAt(position);
            listNeedToDelete.add(list.get(position));
            list.remove(position);
        }
        resetOrder();
    }

    /**
     * @param view
     * @return 搜索view在其父布局中的位置，找不到的话返回-1
     */
    private int getPositionInparent(View view) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (viewGroup.getChildAt(i) == view) {
                return i;
            }
        }
        return CannotSearch;
    }

    /**
     * 点击添加按钮
     */
    @OnClick(R.id.ivAddCurrentHealthyProblem)
    public void onClick() {
        healthyRecordNewActivityController.add(id, category);
    }


    private void log(String message) {
        Log.d("HealthyRecord", message);
    }
}
