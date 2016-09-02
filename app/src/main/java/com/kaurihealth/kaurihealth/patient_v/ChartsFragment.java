package com.kaurihealth.kaurihealth.patient_v;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.utils.Utils;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.eventbus.ChartsFgtStrEvent;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/8/31.
 * <p/>
 * 描述：
 */
public class ChartsFragment extends Fragment {

    @Bind(R.id.line_chart)
    LineChart mLineChart;

    public static Fragment newInstance() {
        return new ChartsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts, container, false);
        ButterKnife.bind(this, view);
        initChartsView();
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 订阅
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ChartsFgtStrEvent event) {
        String message = event.getMessage();
        drawIndexDataCharts(message);
    }

    private void initChartsView() {
        // 设置背景
        mLineChart.setBackgroundColor(Color.WHITE);
        // 是否显示表格颜色
        mLineChart.setDrawGridBackground(true);
        mLineChart.setNoDataTextDescription("请选择监测项目");
        mLineChart.setDescription("时间");
        // 设置是否可以触摸
        mLineChart.setTouchEnabled(true);
        // 是否可以拖拽
        mLineChart.setDragEnabled(true);
        // 是否可以缩放
        mLineChart.setScaleEnabled(true);
        mLineChart.setDoubleTapToZoomEnabled(true);
        mLineChart.setPinchZoom(true);

        YAxis leftAxis = mLineChart.getAxisLeft(); // 得到图表的左侧Y轴实例
        leftAxis.enableGridDashedLine(6f, 3f, 0); // 设置横向表格为虚线
        leftAxis.setDrawLimitLinesBehindData(false);
        YAxis right = mLineChart.getAxisRight();
        right.enableGridDashedLine(6f, 3f, 0);
        right.setDrawLimitLinesBehindData(false);
    }


    public Map<String, List<LongTermMonitoringDisplayBean>> getArrayMap() {
        LongHealthyStandardActivity activity = (LongHealthyStandardActivity) this.getActivity();
        return activity.getArrayMap();
    }

    public void drawIndexDataCharts(String program) {
        Map<String, List<LongTermMonitoringDisplayBean>> mArrayMap = getArrayMap();
        if (mArrayMap == null || mArrayMap.isEmpty())
            throw new IllegalStateException("mArrayMap must be init");

        if (program.equals(Global.Environment.BLOOD)) {
            List<LongTermMonitoringDisplayBean> shrinkageList = mArrayMap.get(Global.Environment.BLOOD_SHRINKAGE);
            List<LongTermMonitoringDisplayBean> diastolicList = mArrayMap.get(Global.Environment.BLOOD_DIASTOLIC);
        } else {
            List<LongTermMonitoringDisplayBean> beanList = mArrayMap.get(program);
            if (beanList.size() == 0) return;
            LongTermMonitoringDisplayBean bean = beanList.get(0);
            //升序列
            Collections.sort(beanList, new Comparator<LongTermMonitoringDisplayBean>() {
                @Override
                public int compare(LongTermMonitoringDisplayBean bean1, LongTermMonitoringDisplayBean bean2) {
                    return bean1.getDate().getTime() - bean2.getDate().getTime() < 0 ? -1 : bean1.getDate().getTime() - bean2.getDate().getTime() > 0 ? 1 : 0;
                }
            });

            List<Entry> entries = new ArrayList<>();
            float count = 0f;
            List<String> chartLabels = new ArrayList<>();
            for (LongTermMonitoringDisplayBean b : beanList) {
                entries.add(new Entry(count++, Float.valueOf(b.getMeasurement())));
                String date = DateUtils.getFormatDate(b.getDate());
                chartLabels.add(date);
            }


            XAxis xAxis = mLineChart.getXAxis();
            xAxis.setDrawAxisLine(true);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawLabels(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new MyXAxisValueFormatter(chartLabels));
            xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
            xAxis.setAvoidFirstLastClipping(true);//在绘制时会避免“剪掉”

            LineDataSet dataSet;
            if (mLineChart.getData() != null && mLineChart.getData().getDataSetCount() > 0) {
                dataSet = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
                dataSet.setValues(entries);
                mLineChart.getData().notifyDataChanged();
                mLineChart.notifyDataSetChanged();
            } else {
                String legend = bean.getType() + "(" + bean.getUnit() + ")" + getString(R.string.charts_null) + "提示:双击图表可以左右移动";
                dataSet = new LineDataSet(entries, legend);
                dataSet.enableDashedLine(10f, 5f, 0f);
                dataSet.enableDashedHighlightLine(10f, 5f, 0f);
                dataSet.setColor(Color.BLACK);
                dataSet.setCircleColor(Color.BLACK);
                dataSet.setLineWidth(1f);
                dataSet.setCircleRadius(3f);
                dataSet.setDrawCircleHole(false);
                dataSet.setValueTextSize(9f);
                dataSet.setDrawFilled(true);
                dataSet.setHighlightEnabled(true);//选中高亮
                if (Utils.getSDKInt() >= 18) {
                    // fill drawable only supported on api level 18 and above
                    Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bg_charts_fade_red);
                    dataSet.setFillDrawable(drawable);
                } else {
                    dataSet.setFillColor(Color.BLACK);
                }

                //            dataSet.setDrawValues(true); // 是否在点上绘制Value
                //            dataSet.setValueTextColor(getResources().getColor(R.color.color_title_back));
                //            dataSet.setCircleHoleRadius(3f);

                // 设置比例图标示，就是那个一组y的value的

                //            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                //            dataSets.add(dataSet);

                LineData data = new LineData(dataSet);
                mLineChart.setData(data);
                mLineChart.invalidate(); // refresh
                mLineChart.animateY(1000); // 从X轴进入的动画

                Legend mLegend = mLineChart.getLegend();
                mLegend.setEnabled(true);
                // 样式
                mLegend.setForm(Legend.LegendForm.CIRCLE);
                mLegend.setFormSize(8f);// 字体
                mLegend.setTextColor(getResources().getColor(R.color.color_title_back));// 颜色
                mLegend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
                mLegend.setXEntrySpace(10f);// 条目的水平间距
                mLegend.setYEntrySpace(10f);// 条目的垂直间距
                mLegend.setWordWrapEnabled(true); //坐标线描述 是否 不允许出边界
                mLegend.setFormToTextSpace(10f);
                mLegend.setMaxSizePercent(0.95f);

                int[] mColors = new int[]{
                        Color.rgb(255, 0, 0)   //红色
                        //                        Color.rgb(2, 196, 244),    //淡蓝色
                        //                        Color.rgb(2, 123, 243)    //深蓝
                };
                mLegend.setCustom(mColors, new String[]{"Set1"});
            }
        }
    }


    class MyXAxisValueFormatter implements AxisValueFormatter {
        private List<String> mValues;

        public MyXAxisValueFormatter(List<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the positin of the label on the axis (x or y)
            return mValues.get((int) value % mValues.size());
        }

        /**
         * this is only needed if numbers are returned, else return 0
         */
        @Override
        public int getDecimalDigits() {
            return 0;
        }
    }
}
