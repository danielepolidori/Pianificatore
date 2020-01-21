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
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;

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

























        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 8f));
        entries.add(new BarEntry(2, 6f));
        //entries.add(new BarEntry(3, 12f));
        //entries.add(new BarEntry(4, 18f));
        //entries.add(new BarEntry(5, 9f));
        entries.add(new BarEntry(6, 4f));
        entries.add(new BarEntry(7, 8f));
        entries.add(new BarEntry(8, 6f));
        //entries.add(new BarEntry(9, 12f));
        //entries.add(new BarEntry(10, 18f));
        //entries.add(new BarEntry(11, 9f));
        entries.add(new BarEntry(12, 4f));
        entries.add(new BarEntry(30, 4f));

        BarDataSet dataset = new BarDataSet(entries, "Numero di attività da svolgere per mese");


        /*
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        */


        BarChart chart = new BarChart(this);
        setContentView(chart);

        //BarData data = new BarData(labels, dataset);
        //chart.setData(data);

        //chart.setDescription("# of times Alice called Bob");



        // ------------

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add((IBarDataSet) dataset);
        BarData data2 = new BarData(dataSets);

        chart.setData(data2);


        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
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
