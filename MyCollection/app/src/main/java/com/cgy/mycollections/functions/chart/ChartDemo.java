package com.cgy.mycollections.functions.chart;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.sqlite.db.DBOperator;
import com.cgy.mycollections.functions.sqlite.db.UserAccount;
import com.cgy.mycollections.utils.Typefaces;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import appframe.utils.L;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * 该页面展示了数据库操作的demo
 */
public class ChartDemo extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.price_change_chart)
    LineChart mPriceChangeChart;
    @BindView(R.id.deal_chart)
    LineChart mDealChart;

    private Typeface mTf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);

        mTf = Typefaces.getOpenSans(this);

        setSupportActionBar(toolbar);
        setTitle("无锡房产图表");

        setUpWuxiHouseMarketChart(mPriceChangeChart, generateHousingPriceChangeData());//涨价降价数
        setUpWuxiHouseMarketChart(mDealChart, generateHousingNetDealData());//网签数
    }

    /**
     * 涨价降价房源数
     *
     * @return
     */
    private LineData generateHousingPriceChangeData() {
        //涨价房源数
        List<Entry> houseWithPriceRiseCount = Arrays.asList(
                new Entry(9, 1269),
                new Entry(10, 1145),
                new Entry(12, 970));
        //降价房源数
        List<Entry> houseWithPriceCutCount = Arrays.asList(
                new Entry(9, 2605),
                new Entry(10, 3091),
                new Entry(12, 2671));

//        MATERIAL_COLORS 0-绿色  1-黄色   2-红   3-蓝
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(generateLineDataSet("涨价房源数", houseWithPriceRiseCount, ColorTemplate.MATERIAL_COLORS[2]));
        sets.add(generateLineDataSet("降价房源数", houseWithPriceCutCount, ColorTemplate.MATERIAL_COLORS[0]));
        return new LineData(sets);
    }

    /**
     * 涨价降价房源数
     *
     * @return
     */
    private LineData generateHousingNetDealData() {
        //二手房网签数
        List<Entry> d1 = Arrays.asList(
                new Entry(9, 6125),
                new Entry(10, 5174),
                new Entry(11, 4963),
                new Entry(12, 4421));
        //新房网签数
        List<Entry> d2 = Arrays.asList(
                new Entry(9, 8307),
                new Entry(10, 5856),
                new Entry(11, 5627),
                new Entry(12, 3884));

//        COLORFUL_COLORS 0-深红  1-浅红，橙色   2-  3-
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(generateLineDataSet("二手房网签数", d1, ColorTemplate.COLORFUL_COLORS[2]));
        sets.add(generateLineDataSet("新房网签数", d2, ColorTemplate.COLORFUL_COLORS[3]));
        return new LineData(sets);
    }

    /**
     * 根据数据绘制chart
     *
     * @param chart
     * @param data
     */
    private void setUpWuxiHouseMarketChart(LineChart chart, LineData data) {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // set data
        chart.setData(data);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        chart.animateX(750);
    }


    /**
     * 生成线条（名称，数据，颜色）
     *
     * @param name
     * @param values
     * @param colorInt
     * @return
     */
    private LineDataSet generateLineDataSet(String name, List<Entry> values, int colorInt) {
        LineDataSet lineDataSet = new LineDataSet(values, name);
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setCircleRadius(4.5f);
        lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));

        lineDataSet.setColor(colorInt);
        lineDataSet.setCircleColor(colorInt);
        lineDataSet.setDrawValues(true);
        return lineDataSet;
    }

}
