package com.kaurihealth.kaurihealth.patient_v.long_monitoring;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.kaurihealth.utilslib.date.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/8/31.
 * <p>
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
        removeStickyEvent(ChartsFgtStrEvent.class);
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    protected <T> void removeStickyEvent(Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent((T) stickyEvent);
        }
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

    /**
     * 订阅 用于显示的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ChartsFgtStrEvent event) {
        List<List<LongTermMonitoringDisplayBean>> lists = event.getListsMessage();
        drawIndexDataCharts(lists);
    }

    public void drawIndexDataCharts(List<List<LongTermMonitoringDisplayBean>> lists) {
        if (lists == null || lists.isEmpty() || lists.size() == 0 || lists.size() == 1 && lists.get(0).isEmpty()) {
            return;
        }

        if (lists.size() == 2) {
            List<LongTermMonitoringDisplayBean> beanList = lists.get(0);
            if (beanList.size() == 0) {
                Toast.makeText(this.getContext(), "数据缺失,暂不显示选择项", Toast.LENGTH_SHORT).show();
                return;
            }
            LongTermMonitoringDisplayBean bean = beanList.get(0);
            //升序列
            Collections.sort(beanList, (bean1, bean2) -> {
                long mLong = bean1.getDate().getTime() - bean2.getDate().getTime();
                return mLong < 0 ? -1 : mLong > 0 ? 1 : 0;
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
            xAxis.setGranularity(getLabelSize(chartLabels)); // minimum axis-step (interval) is 1
            xAxis.setAvoidFirstLastClipping(true);//在绘制时会避免“剪掉”

            List<LongTermMonitoringDisplayBean> beanList_2 = lists.get(1);
            if (beanList_2.size() == 0) {
                Toast.makeText(this.getContext(), "数据缺失,暂不显示选择项", Toast.LENGTH_SHORT).show();
                return;
            }
            //升序列
            Collections.sort(beanList_2, (bean1, bean2) -> {
                long mLong = bean1.getDate().getTime() - bean2.getDate().getTime();
                return mLong < 0 ? -1 : mLong > 0 ? 1 : 0;
            });
            List<Entry> entries_2 = new ArrayList<>();
            float count_2 = 0f;
            for (LongTermMonitoringDisplayBean b : beanList_2) {
                entries_2.add(new Entry(count_2++, Float.valueOf(b.getMeasurement())));
            }
            LongTermMonitoringDisplayBean bean_2 = beanList_2.get(0);
            LineDataSet dataSet_2;
            String legend_2 = bean_2.getType() + "(" + bean_2.getUnit() + ")" + getString(R.string.charts_null) + "提示:双击图表可以左右移动";

            LineDataSet dataSet;
            String legend = bean.getType() + "(" + bean.getUnit() + ")" + getString(R.string.charts_null) + "提示:双击图表可以左右移动";

            int[] mColors = new int[]{
                    Color.rgb(255, 0, 0),   //红色
                    Color.rgb(2, 123, 243)    //深蓝
            };

            dataSet = new LineDataSet(entries, legend);
            dataSet.enableDashedLine(10f, 5f, 0f);
            dataSet.enableDashedHighlightLine(10f, 5f, 0f);
            dataSet.setColor(mColors[0]);
            dataSet.setCircleColor(mColors[0]);
            dataSet.setLineWidth(1f);
            dataSet.setCircleRadius(3f);
            dataSet.setDrawCircleHole(false);
            dataSet.setValueTextSize(9f);
            dataSet.setDrawFilled(true);
            dataSet.setHighlightEnabled(true);//选中高亮

            dataSet_2 = new LineDataSet(entries_2, legend_2);
            dataSet_2.enableDashedLine(10f, 5f, 0f);
            dataSet_2.enableDashedHighlightLine(10f, 5f, 0f);
            dataSet_2.setColor(mColors[1]);
            dataSet_2.setCircleColor(mColors[1]);
            dataSet_2.setLineWidth(1f);
            dataSet_2.setCircleRadius(3f);
            dataSet_2.setDrawCircleHole(false);
            dataSet_2.setValueTextSize(9f);
            dataSet_2.setDrawFilled(true);
            dataSet_2.setHighlightEnabled(true);//选中高亮

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bg_charts_fade_red);
                dataSet.setFillDrawable(drawable);
            } else {
                dataSet.setFillColor(Color.BLACK);
            }
            Legend mLegend = mLineChart.getLegend();
            mLegend.setEnabled(true);// 样式
            mLegend.setForm(Legend.LegendForm.CIRCLE);
            mLegend.setFormSize(8f);// 字体
            mLegend.setTextColor(getResources().getColor(R.color.color_title_back));// 颜色
            mLegend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
            mLegend.setXEntrySpace(10f);// 条目的水平间距
            mLegend.setYEntrySpace(10f);// 条目的垂直间距
            mLegend.setWordWrapEnabled(true); //坐标线描述 是否 不允许出边界
            mLegend.setFormToTextSpace(10f);
            mLegend.setMaxSizePercent(0.95f);
            mLegend.setCustom(mColors, new String[]{legend, legend_2});

            LineData data = new LineData(dataSet, dataSet_2);
            mLineChart.setData(data);
            mLineChart.invalidate(); // refresh
            mLineChart.animateY(1000); // 从X轴进入的动画
        } else if (lists.size() == 1) {
            List<LongTermMonitoringDisplayBean> beanList = lists.get(0);
            if (beanList.size() == 0) return;
            LongTermMonitoringDisplayBean bean = beanList.get(0);
            //升序列
            Collections.sort(beanList, (bean1, bean2) -> {
                long mLong = bean1.getDate().getTime() - bean2.getDate().getTime();
                return mLong < 0 ? -1 : mLong > 0 ? 1 : 0;
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
            xAxis.setGranularity(getLabelSize(chartLabels)); // minimum axis-step (interval) is 1
            xAxis.setAvoidFirstLastClipping(true);//在绘制时会避免“剪掉”

            LineDataSet dataSet;
            String legend = bean.getType() + "(" + bean.getUnit() + ")" + getString(R.string.charts_null) + "提示:双击图表可以左右移动";

            int[] mColors = new int[]{
                    Color.rgb(255, 0, 0)   //红色
                    //                        Color.rgb(2, 196, 244),    //淡蓝色
                    //                        Color.rgb(2, 123, 243)    //深蓝
            };
//            if (mLineChart.getData() != null && mLineChart.getData().getDataSetCount() > 0) {
//                dataSet = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
//                dataSet.setValues(entries);
//                mLineChart.getData().notifyDataChanged();
//                mLineChart.notifyDataSetChanged();
//                mLineChart.invalidate();
//            } else {
            dataSet = new LineDataSet(entries, legend);
            dataSet.enableDashedLine(10f, 5f, 0f);
            dataSet.enableDashedHighlightLine(10f, 5f, 0f);
            dataSet.setColor(mColors[0]);
            dataSet.setCircleColor(Color.BLACK);
            dataSet.setLineWidth(1f);
            dataSet.setCircleRadius(3f);
            dataSet.setDrawCircleHole(false);
            dataSet.setValueTextSize(9f);
            dataSet.setDrawFilled(true);
            dataSet.setHighlightEnabled(true);//选中高亮
            if (Utils.getSDKInt() >= 18) {// fill drawable only supported on api level 18 and above
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
            mLegend.setCustom(mColors, new String[]{legend});

            LineData data = new LineData(dataSet);
            mLineChart.setData(data);
            mLineChart.invalidate(); // refresh
            mLineChart.animateY(1000); // 从X轴进入的动画
        }
    }

    private float getLabelSize(List<String> labels) {
        int size = labels.size();
        if (size == 1) {
            return 2f;
        } else if (size < 10 && size > 5) {
            return 2f;
        } else if (size >= 10) {
            return size / 5;
        }
        return 1f;
    }


    class MyXAxisValueFormatter implements AxisValueFormatter {
        private List<String> mValues;

        public MyXAxisValueFormatter(List<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the positin of the label on the axis (x or y)
            Log.e("TAG", String.valueOf(value));
            Log.e("TAG", "==================" + mValues.get((int) value % mValues.size()));
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
