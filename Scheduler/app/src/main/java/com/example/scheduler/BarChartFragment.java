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


    public BarChartFragment() {
        // Required empty public constructor
    }

    public static BarChartFragment newInstance() {

        BarChartFragment fragment = new BarChartFragment();

        /*
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        */

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


        // Di default inserire il valore 0 se non si ha alcun valore, così che il campo venga comunque rappresentato nel diagramma
        float gen_numTask = 8;
        float feb_numTask = 5;
        float mar_numTask = 0;
        float apr_numTask = 0;
        float mag_numTask = 1;
        float giu_numTask = 6;
        float lug_numTask = 0;
        float ago_numTask = 2;
        float set_numTask = 3;
        float ott_numTask = 5;
        float nov_numTask = 0;
        float dic_numTask = 0;

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
        barChart.setData(data);

        barChart.getDescription().setText("Attività da svolgere in ogni mese (dell'anno corrente)");

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

        
        return view;
    }
}
