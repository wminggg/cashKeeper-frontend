package com.android.frontend.fragment.chartFragment;

import android.graphics.Color;
import android.view.View;

import com.android.frontend.db.BarChartItemBean;
import com.android.frontend.db.DBManager;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

public class OutcomChartFragment extends BaseChartFragment {

    int kind = 0;

    @Override
    public void onResume() {
        super.onResume();
        loadData(year, month, kind);
    }

    @Override
    protected void setAxisData(int year, int month) {
        List<BarEntry> barEntries = new ArrayList<>();
        List<BarChartItemBean> list = DBManager.getSumMoneyOneDayInMonth(year, month, kind);

        if (list.isEmpty()) {
            barChart.setVisibility(View.GONE);
            chartTv.setVisibility(View.VISIBLE);
        } else {
            barChart.setVisibility(View.VISIBLE);
            chartTv.setVisibility(View.GONE);

            for (int i = 0; i < 31; i++) {
                barEntries.add(new BarEntry(i, 0.0f));
            }

            for (BarChartItemBean itemBean : list) {
                int day = itemBean.getDay();
                int xIndex = day - 1;
                BarEntry barEntry = barEntries.get(xIndex);
                barEntry.setY(itemBean.getSummoney());
            }

            BarDataSet barDataSet = new BarDataSet(barEntries, "");
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(8f);
            barDataSet.setColor(Color.RED);

            barDataSet.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return value == 0 ? "" : String.valueOf(value);
                }
            });

            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(0.2f);
            barChart.setData(barData);
        }
    }

    @Override
    protected void setYAxis(int year, int month) {
        float maxMoney = DBManager.getMaxMoneyOneDayInMonth(year, month, kind);
        float max = (float) Math.ceil(maxMoney);

        YAxis yAxis_right = barChart.getAxisRight();
        yAxis_right.setAxisMaximum(max);
        yAxis_right.setAxisMinimum(0f);
        yAxis_right.setEnabled(false);

        YAxis yAxis_left = barChart.getAxisLeft();
        yAxis_left.setAxisMaximum(max);
        yAxis_left.setAxisMinimum(0f);
        yAxis_left.setEnabled(false);

        barChart.getLegend().setEnabled(false);
    }

    @Override
    public void setDate(int year, int month) {
        super.setDate(year, month);
        loadData(year, month, kind);
    }
}
