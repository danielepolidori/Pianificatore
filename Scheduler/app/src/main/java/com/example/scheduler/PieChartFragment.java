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

    private static float fam_numTask;
    private static float lav_numTask;
    private static float tempLib_numTask;
    private static float altro_numTask;


    public PieChartFragment() {
        // Required empty public constructor
    }

    public static PieChartFragment newInstance(int nTask_fam, int nTask_lav, int nTask_tempLib, int nTask_altro) {

        PieChartFragment fragment = new PieChartFragment();

        fam_numTask = nTask_fam;
        lav_numTask = nTask_lav;
        tempLib_numTask = nTask_tempLib;
        altro_numTask = nTask_altro;

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

        if((fam_numTask > 0) || (lav_numTask > 0) || (tempLib_numTask > 0) || (altro_numTask > 0))
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