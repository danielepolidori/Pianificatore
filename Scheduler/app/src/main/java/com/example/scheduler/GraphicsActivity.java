package com.example.scheduler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
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



        // Istogramma


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

        ArrayList<BarEntry> valori = new ArrayList<>();
        valori.add(new BarEntry(1, gen_numTask, "Gennaio"));
        valori.add(new BarEntry(2, feb_numTask));
        valori.add(new BarEntry(3, mar_numTask));
        valori.add(new BarEntry(4, apr_numTask));
        valori.add(new BarEntry(5, mag_numTask));
        valori.add(new BarEntry(6, giu_numTask));
        valori.add(new BarEntry(7, lug_numTask));
        valori.add(new BarEntry(8, ago_numTask));
        valori.add(new BarEntry(9, set_numTask));
        valori.add(new BarEntry(10, ott_numTask));
        valori.add(new BarEntry(11, nov_numTask));
        valori.add(new BarEntry(12, dic_numTask));    // Di default inserire il valore 0, così che mostri comunque l'asse X fino al punto 12

        BarDataSet dataset = new BarDataSet(valori, "Numero di attività");


        //BarChart chart = new BarChart(this);
        //setContentView(chart);
        BarChart chart = findViewById(R.id.barchart);

        BarData data = new BarData(dataset);
        chart.setData(data);

        chart.getDescription().setText("Attività da svolgere in ogni mese (dell'anno corrente)");


        // Abbellimenti grafici

        XAxis xAxis = chart.getXAxis();
        YAxis yAxisRight = chart.getAxisRight();
        YAxis yAxisLeft = chart.getAxisLeft();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      // Asse X in basso nello schermo
        xAxis.setLabelCount(12);                            // Mostra 12 numeri dell'asse X
        chart.setScaleEnabled(false);                       // Rimuove la possibilità di fare zoom
        yAxisRight.setEnabled(false);                       // Rimuove l'asse Y destro
        yAxisLeft.setDrawLabels(false);                     // Rimuove i numeri dell'asse Y sinistro
        yAxisLeft.setDrawGridLines(false);                  // Rimuove la griglia retrostante
        chart.animateY(1500);                   // Aggiunge l'animazione alle barre del grafico
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
