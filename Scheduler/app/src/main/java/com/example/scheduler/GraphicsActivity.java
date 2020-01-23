package com.example.scheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class GraphicsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);


        // Toolbar

        Toolbar toolbar = findViewById(R.id.toolbar_graf);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_48dp);


        // Navigation Drawer

        mDrawerLayout = findViewById(R.id.drawer_layout_graf);

        NavigationView navigationView = findViewById(R.id.nav_view_graf);

        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        Intent i;
                        switch (menuItem.getItemId()) {

                            case R.id.item_home:

                                i = new Intent(GraphicsActivity.this, MainActivity.class);
                                startActivity(i);

                                break;

                            case R.id.item_crono:

                                //~ ...

                                break;

                            case R.id.item_graf:

                                break;

                            case R.id.item_cred:

                                i = new Intent(GraphicsActivity.this, CreditsActivity.class);
                                startActivity(i);

                                break;

                            default:
                                //~ ...
                        }

                        return true;
                    }
                });



        // Bottom Navigation View

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_bar:

                        //~ ...

                        break;

                    case R.id.action_pie:

                        //~ ...

                        break;
                }

                return true;
            }
        });



        /*
        // Istogramma


        BarChart barChart = findViewById(R.id.barchart);


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

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      // Asse X in basso nello schermo
        xAxis.setLabelCount(12);                            // Mostra 12 numeri dell'asse X
        barChart.setScaleEnabled(false);                       // Rimuove la possibilità di fare zoom
        yAxisRight.setEnabled(false);                       // Rimuove l'asse Y destro
        yAxisLeft.setDrawLabels(false);                     // Rimuove i numeri dell'asse Y sinistro
        yAxisLeft.setDrawGridLines(false);                  // Rimuove la griglia retrostante
        barChart.animateY(1500);                               // Aggiunge l'animazione alle barre del grafico
        */



        // Diagramma a torta


        PieChart pieChart = findViewById(R.id.piechart);


        float fam_numTask = 1;
        float lav_numTask = 3;
        float tempLib_numTask = 2;
        float altro_numTask = 4;


        ArrayList<PieEntry> valori_pie = new ArrayList<>();
        valori_pie.add(new PieEntry(fam_numTask));
        valori_pie.add(new PieEntry(lav_numTask));
        valori_pie.add(new PieEntry(tempLib_numTask));
        valori_pie.add(new PieEntry(altro_numTask));

        PieDataSet dataSet = new PieDataSet(valori_pie, "Percentuale sul numero di attività totali");


        PieData data = new PieData(dataSet);
        //data.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));
        pieChart.setData(data);

        pieChart.getDescription().setText("Percentuale delle attività (da svolgere) divise nelle ripettive classi d'appartenenza.");
        pieChart.getDescription().setTextSize(10f);     // sets the size of the label text in density pixels min = 6f, max = 24f, default is 10f, font size will be in dp

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}