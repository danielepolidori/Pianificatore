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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class GraphicsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private int genNumTask;
    private int febNumTask;
    private int marNumTask;
    private int aprNumTask;
    private int magNumTask;
    private int giuNumTask;
    private int lugNumTask;
    private int agoNumTask;
    private int setNumTask;
    private int ottNumTask;
    private int novNumTask;
    private int dicNumTask;

    private int famNumTask;
    private int lavNumTask;
    private int tempLibNumTask;
    private int altroNumTask;


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



        // Raccolta dei dati

        genNumTask = getIntent().getIntExtra("num_task_gen", -1);
        febNumTask = getIntent().getIntExtra("num_task_feb", -1);
        marNumTask = getIntent().getIntExtra("num_task_mar", -1);
        aprNumTask = getIntent().getIntExtra("num_task_apr", -1);
        magNumTask = getIntent().getIntExtra("num_task_mag", -1);
        giuNumTask = getIntent().getIntExtra("num_task_giu", -1);
        lugNumTask = getIntent().getIntExtra("num_task_lug", -1);
        agoNumTask = getIntent().getIntExtra("num_task_ago", -1);
        setNumTask = getIntent().getIntExtra("num_task_set", -1);
        ottNumTask = getIntent().getIntExtra("num_task_ott", -1);
        novNumTask = getIntent().getIntExtra("num_task_nov", -1);
        dicNumTask = getIntent().getIntExtra("num_task_dic", -1);

        famNumTask = getIntent().getIntExtra("num_task_fam", -1);
        lavNumTask = getIntent().getIntExtra("num_task_lav", -1);
        tempLibNumTask = getIntent().getIntExtra("num_task_templib", -1);
        altroNumTask = getIntent().getIntExtra("num_task_altro", -1);



        // Bottom Navigation View

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()) {

                    case R.id.action_barchart:

                        if ((genNumTask > -1) && (febNumTask > -1) && (marNumTask > -1) && (aprNumTask > -1) && (magNumTask > -1) && (giuNumTask > -1) && (lugNumTask > -1) && (agoNumTask > -1) && (setNumTask > -1) && (ottNumTask > -1) && (novNumTask > -1) && (dicNumTask > -1))
                            selectedFragment = BarChartFragment.newInstance(genNumTask, febNumTask, marNumTask, aprNumTask, magNumTask, giuNumTask, lugNumTask, agoNumTask, setNumTask, ottNumTask, novNumTask, dicNumTask);
                        else
                            System.out.println("ERRORE_DATI_INTENT_GRAF");

                        break;

                    case R.id.action_piechart:

                        if ((famNumTask > -1) && (lavNumTask > -1) && (tempLibNumTask > -1) && (altroNumTask > -1))
                            selectedFragment = PieChartFragment.newInstance(famNumTask, lavNumTask, tempLibNumTask, altroNumTask);
                        else
                            System.out.println("ERRORE_DATI_INTENT_GRAF");

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
        transaction.replace(R.id.frame_layout, BarChartFragment.newInstance(genNumTask, febNumTask, marNumTask, aprNumTask, magNumTask, giuNumTask, lugNumTask, agoNumTask, setNumTask, ottNumTask, novNumTask, dicNumTask));
        transaction.commit();
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