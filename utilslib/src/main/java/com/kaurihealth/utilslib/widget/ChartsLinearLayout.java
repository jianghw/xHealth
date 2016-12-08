package com.kaurihealth.utilslib.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.kaurihealth.utilslib.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jianghw on 2016/9/1.
 * <p/>
 * 描述：
 */
public class ChartsLinearLayout extends LinearLayout {
    private final Context context;

    public ChartsLinearLayout(Context context) {
        this(context, null);
    }

    public ChartsLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartsLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void init(HashMap<Integer, Integer[]> hashMap) {
        /**拿控件的高度*/
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //设置父类控件的方向
        setOrientation(LinearLayout.VERTICAL);

        /**创建子控件，并标记*/

    }

    private UIChartsItem getChileItem() {
        UIChartsItem chartsItem = new UIChartsItem();
        chartsItem.mParent = LayoutInflater.from(context).inflate(R.layout.ui_charts_linearlayout, null);
        LineChart mLineChart = (LineChart) chartsItem.mParent.findViewById(R.id.line_chart);

        // 设置背景
        mLineChart.setBackgroundColor(Color.WHITE);
        // 是否显示表格颜色
        mLineChart.setDrawGridBackground(true);
        mLineChart.setNoDataTextDescription("并无数据");
        mLineChart.setDescription("时间");
        // 设置是否可以触摸
        mLineChart.setTouchEnabled(true);
        // 是否可以拖拽
        mLineChart.setDragEnabled(true);
        // 是否可以缩放
        mLineChart.setScaleEnabled(true);
        mLineChart.setDoubleTapToZoomEnabled(true);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
//        xAxis.setValueFormatter(new MyXAxisValueFormatter(chartLabels));
        xAxis.enableGridDashedLine(6f, 2f, 0f); // 设置横向表格为虚线
        xAxis.setAvoidFirstLastClipping(true);
        return null;
    }


    public void initChart() {
        /**拿控件的高度*/
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //设置父类控件的方向
        setOrientation(LinearLayout.VERTICAL);


        UIChartsItem chartsItem = new UIChartsItem();
        chartsItem.mParent = LayoutInflater.from(context).inflate(R.layout.ui_charts_linearlayout, null);
        LineChart mLineChart = (LineChart) chartsItem.mParent.findViewById(R.id.line_chart);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 200));
        entries.add(new Entry(1f, 400));
        entries.add(new Entry(2f, 80));

        List<String> chartLabels = new ArrayList<>();
        chartLabels.add("2018-01-13");
        chartLabels.add("2018-02-22");
        chartLabels.add("2018-04-02");

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new MyXAxisValueFormatter(chartLabels));
        xAxis.enableGridDashedLine(8f, 2f, 0f); // 设置横向表格为虚线
        xAxis.setAvoidFirstLastClipping(true);


        YAxis leftAxis = mLineChart.getAxisLeft(); // 得到图表的左侧Y轴实例
        leftAxis.setStartAtZero(false); // 设置图表起点从0开始
        leftAxis.enableGridDashedLine(10f, 3f, 0); // 设置横向表格为虚线
        leftAxis.setDrawLimitLinesBehindData(false);
        leftAxis.setLabelCount(1, false);
        YAxis right = mLineChart.getAxisRight(); // 得到图表的右侧Y轴实例
        right.setStartAtZero(false); // 设置图表起点从0开始
        right.enableGridDashedLine(10f, 3f, 0); // 设置横向表格为虚线
        right.setDrawLimitLinesBehindData(false);


        LineDataSet dataSet = new LineDataSet(entries, "测试折线图");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLUE);
        dataSet.enableDashedLine(10f, 3f, 0);
        dataSet.setCircleRadius(3f);// 显示的圆形大小
        dataSet.setCircleColor(Color.BLACK);// 圆形的颜色
        dataSet.setCircleHoleRadius(3f);
        dataSet.setHighLightColor(Color.GREEN); // 高亮的线的颜色

        dataSet.setHighlightLineWidth(1f);
        dataSet.setHighlightEnabled(true);
        //填充
        //        dataSet.setDrawFilled(true);
        //        dataSet.setFillColor(Color.YELLOW & 0x70FFFFFF);
        //        dataSet.setFillFormatter(new DefaultFillFormatter());
        //        dataSet.setFillDrawable(getResources().getDrawable(R.drawable.line_chart_solid));

        List<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(dataSet);

        LineData lineData = new LineData(lineDataSets);
        mLineChart.setData(lineData);
        mLineChart.animateY(1000); // 从Y轴进入的动画
        //是否在折线图上添加边框
        mLineChart.setDrawBorders(false);
        // 如果没有数据的时候，会显示这个
        mLineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // 设置背景
        mLineChart.setBackgroundColor(Color.WHITE);
        // 是否显示表格颜色
        mLineChart.setDrawGridBackground(true);
        mLineChart.setGridBackgroundColor(Color.GREEN);
        mLineChart.setDescription("时间");

        // 设置比例图标示，就是那个一组y的value的
        Legend mLegend = mLineChart.getLegend();
        mLegend.setEnabled(true);
        // 样式
        mLegend.setForm(Legend.LegendForm.CIRCLE);
        mLegend.setFormSize(8f);// 字体
        mLegend.setTextColor(Color.BLUE);// 颜色
        mLegend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        mLegend.setXEntrySpace(20f);// 条目的水平间距
        mLegend.setYEntrySpace(20f);// 条目的垂直间距
        mLegend.setWordWrapEnabled(true); //坐标线描述 是否 不允许出边界
        mLegend.setMaxSizePercent(0.95f);

        mLineChart.setAutoScaleMinMaxEnabled(true);//y轴的值自适应
        mLineChart.setDragDecelerationEnabled(true);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        mLineChart.setDragDecelerationFrictionCoef(0.99f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。
        mLineChart.setPinchZoom(true);//设置是否能扩大扩小
        mLineChart.fitScreen();
        // 设置网格底下的那条线的颜色
        mLineChart.setBorderColor(Color.GRAY);

        // 设置是否可以触摸
        mLineChart.setTouchEnabled(true);
        // 是否可以拖拽
        mLineChart.setDragEnabled(true);
        // 是否可以缩放
        mLineChart.setScaleEnabled(true);
        mLineChart.setDoubleTapToZoomEnabled(true);
        mLineChart.invalidate(); // refresh

        addView(chartsItem.mParent, layoutParams);
        invalidate();
    }


    private class UIChartsItem {
        View mParent; //父控件
        LineChart mLineChart;
    }

    class MyXAxisValueFormatter implements AxisValueFormatter {
        private List<String> mValues;

        public MyXAxisValueFormatter(List<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return mValues.get((int) value);
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
