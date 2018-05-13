package com.example.asus.LirikByMe;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int[] yData = {0, 0, 0, 0, 0, 0, 0, 0};
    private String[] xData = {"K-Pop", "Metal", "Pop", "Dangdut", "Rock", "Hip-Hop", "Rap", "Jazz",};
    PieChart pieChart;
    SQLiteAdapter sqLiteAdapter;

    public ChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment ChartFragment.
     */
    public static ChartFragment newInstance(int sectionNumber) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_main, container, false);
        pieChart = (PieChart) view.findViewById(R.id.PieChart);
        sqLiteAdapter = new SQLiteAdapter(getActivity());

        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Genre");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);

        addDataSet();
        // Inflate the layout for this fragment
        return view;
    }

    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        sqLiteAdapter.openToRead();
        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(i, sqLiteAdapter.selectCountWhereGenre(xData[i])));
            xEntrys.add(String.valueOf(xData[i]));
        }
        sqLiteAdapter.close();
        //create data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Genre");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //data set colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.LTGRAY);
        colors.add(Color.DKGRAY);

        pieDataSet.setColors(colors);

        //add legend
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

