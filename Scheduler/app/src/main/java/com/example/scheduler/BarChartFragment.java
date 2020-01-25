package com.example.scheduler;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;


public class BarChartFragment extends Fragment {

    private BarChart barChart;

    private static float gen_numTask;
    private static float feb_numTask;
    private static float mar_numTask;
    private static float apr_numTask;
    private static float mag_numTask;
    private static float giu_numTask;
    private static float lug_numTask;
    private static float ago_numTask;
    private static float set_numTask;
    private static float ott_numTask;
    private static float nov_numTask;
    private static float dic_numTask;


    public BarChartFragment() {
        // Required empty public constructor
    }

    public static BarChartFragment newInstance(int nTask_gen, int nTask_feb, int nTask_mar, int nTask_apr, int nTask_mag, int nTask_giu, int nTask_lug, int nTask_ago, int nTask_set, int nTask_ott, int nTask_nov, int nTask_dic) {

        BarChartFragment fragment = new BarChartFragment();

        // Di default inserire il valore 0 se non si ha alcun valore, così che il campo venga comunque rappresentato nel diagramma
        gen_numTask = nTask_gen;
        feb_numTask = nTask_feb;
        mar_numTask = nTask_mar;
        apr_numTask = nTask_apr;
        mag_numTask = nTask_mag;
        giu_numTask = nTask_giu;
        lug_numTask = nTask_lug;
        ago_numTask = nTask_ago;
        set_numTask = nTask_set;
        ott_numTask = nTask_ott;
        nov_numTask = nTask_nov;
        dic_numTask = nTask_dic;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment


        // Istogramma


        View view = inflater.inflate(R.layout.fragment_barchart, container, false);

        barChart = (BarChart)view.findViewById(R.id.barchart);


        ArrayList<BarEntry> valori_bar = new ArrayList<>();
        valori_bar.add(new BarEntry(1, gen_numTask));
        valori_bar.add(new BarEntry(2, feb_numTask));
        valori_bar.add(new BarEntry(3, mar_numTask));
        valori_bar.add(new BarEntry(4, apr_numTask));
        valori_bar.add(new BarEntry(5, mag_numTask));
        valori_bar.add(new BarEntry(6, giu_numTask));
        valori_bar.add(new BarEntry(7, lug_numTask));
        valori_bar.add(new BarEntry(8, ago_numTask));
        valori_bar.add(new BarEntry(9, set_numTask));
        valori_bar.add(new BarEntry(10, ott_numTask));
        valori_bar.add(new BarEntry(11, nov_numTask));
        valori_bar.add(new BarEntry(12, dic_numTask));

        BarDataSet dataset = new BarDataSet(valori_bar, "Numero di attività");


        // Riempire il grafico

        BarData data = new BarData(dataset);

        if((gen_numTask > 0) || (feb_numTask > 0) || (mar_numTask > 0) || (apr_numTask > 0) || (mag_numTask > 0) || (giu_numTask > 0) || (lug_numTask > 0) || (ago_numTask > 0) || (set_numTask > 0) || (ott_numTask > 0) || (nov_numTask > 0) || (dic_numTask > 0))
            barChart.setData(data);

        barChart.getDescription().setText("Attività da svolgere in ogni mese (dell'anno corrente)");
        barChart.getDescription().setTextSize(13f);     // sets the size of the label text in density pixels min = 6f, max = 24f, default is 10f, font size will be in dp

        barChart.setNoDataText("Non è ancora presente nessuna attività da analizzare.");


        // Abbellimenti grafici

        XAxis xAxis = barChart.getXAxis();
        YAxis yAxisRight = barChart.getAxisRight();
        YAxis yAxisLeft = barChart.getAxisLeft();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);          // Asse X in basso nello schermo
        xAxis.setLabelCount(12);                                // Mostra 12 numeri dell'asse X
        barChart.setScaleEnabled(false);                        // Rimuove la possibilità di fare zoom
        yAxisRight.setEnabled(false);                           // Rimuove l'asse Y destro
        yAxisLeft.setDrawLabels(false);                         // Rimuove i numeri dell'asse Y sinistro
        yAxisLeft.setDrawGridLines(false);                      // Rimuove la griglia retrostante
        barChart.animateY(1500);                    // Aggiunge l'animazione alle barre del grafico
        barChart.setFitBars(true);                              // make the x-axis fit exactly all bars
        data.setValueTextSize(10f);                             // this increases the values text size


        return view;
    }
}
