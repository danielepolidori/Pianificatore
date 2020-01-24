package com.example.scheduler;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;


public class PieChartFragment extends Fragment {

    private PieChart pieChart;


    public PieChartFragment() {
        // Required empty public constructor
    }

    public static PieChartFragment newInstance() {

        PieChartFragment fragment = new PieChartFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment


        // Diagramma a torta


        View view = inflater.inflate(R.layout.fragment_piechart, container, false);

        pieChart = (PieChart)view.findViewById(R.id.piechart);


        float fam_numTask = 1;
        float lav_numTask = 3;
        float tempLib_numTask = 2;
        float altro_numTask = 4;


        ArrayList<PieEntry> valori_pie = new ArrayList<>();
        if(fam_numTask > 0)
            valori_pie.add(new PieEntry(fam_numTask));
        if(lav_numTask > 0)
            valori_pie.add(new PieEntry(lav_numTask));
        if(tempLib_numTask > 0)
            valori_pie.add(new PieEntry(tempLib_numTask));
        if(altro_numTask > 0)
            valori_pie.add(new PieEntry(altro_numTask));

        PieDataSet dataSet = new PieDataSet(valori_pie, "Percentuale sul numero di attività totali");


        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        pieChart.getDescription().setText("Percentuale delle attività (da svolgere) divise nelle ripettive classi d'appartenenza.");
        pieChart.getDescription().setTextSize(9f);     // sets the size of the label text in density pixels min = 6f, max = 24f, default is 10f, font size will be in dp

        pieChart.setNoDataText("Non è ancora presente nessuna attività da analizzare.");


        Legend legend = pieChart.getLegend();

        LegendEntry entry_fam = new LegendEntry();
        entry_fam.formColor = ColorTemplate.COLORFUL_COLORS[0];
        entry_fam.label = "Famiglia";

        LegendEntry entry_lav = new LegendEntry();
        entry_lav.formColor = ColorTemplate.COLORFUL_COLORS[1];
        entry_lav.label = "Lavoro";

        LegendEntry entry_tempLib = new LegendEntry();
        entry_tempLib.formColor = ColorTemplate.COLORFUL_COLORS[2];
        entry_tempLib.label = "Tempo libero";

        LegendEntry entry_altro= new LegendEntry();
        entry_altro.formColor = ColorTemplate.COLORFUL_COLORS[3];
        entry_altro.label = "Altro";

        ArrayList<LegendEntry> legend_entries = new ArrayList<>();
        legend_entries.add(entry_fam);
        legend_entries.add(entry_lav);
        legend_entries.add(entry_tempLib);
        legend_entries.add(entry_altro);

        legend.setCustom(legend_entries);


        pieChart.setUsePercentValues(true);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(1500, 1500);
        data.setValueTextSize(15f);                                 // this increases the values text size


        return view;
    }
}