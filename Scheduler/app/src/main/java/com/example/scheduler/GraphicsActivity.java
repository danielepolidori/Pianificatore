package com.example.scheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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



        // Bottom Navigation View

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()) {

                    case R.id.action_barchart:

                        selectedFragment = BarChartFragment.newInstance();

                        break;

                    case R.id.action_piechart:

                        //selectedFragment = PieChartFragment.newInstance();

                        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();

                return true;
            }
        });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, BarChartFragment.newInstance());
        transaction.commit();



        /*
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
        */
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