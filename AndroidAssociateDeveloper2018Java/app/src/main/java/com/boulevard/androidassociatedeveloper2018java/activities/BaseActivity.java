package com.boulevard.androidassociatedeveloper2018java.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.boulevard.androidassociatedeveloper2018java.R;
import com.boulevard.androidassociatedeveloper2018java.fragments.BaseFragment;
import com.boulevard.androidassociatedeveloper2018java.fragments.JobSchedulerFragment;
import com.boulevard.androidassociatedeveloper2018java.fragments.ListViewFragment;

public class BaseActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Setup view elements for reference, after view is created
        drawerLayout = findViewById(R.id.drawer_layout);
        mainToolbar = findViewById(R.id.main_toolbar);

        /*
         * Setup custom toolbar (used for launching navigation drawer)
         */
        setSupportActionBar(mainToolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
        actionbar.setTitle("Home");

        /* Auto populate with base fragment */
        /* TODO: Ignore this for screen rotation */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        BaseFragment fragment = BaseFragment.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        /*
         * Navigation Drawer stuff
         */
        NavigationView navigationView = findViewById(R.id.navigation_drawer_view);
        NavigationView.OnNavigationItemSelectedListener listener = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                if(menuItem.getItemId() == R.id.nav_list_view) {

                    ListViewFragment fragment = new ListViewFragment();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                }
                else if(menuItem.getItemId() == R.id.nav_job_scheduler) {

                    JobSchedulerFragment fragment = new JobSchedulerFragment();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                }

                drawerLayout.closeDrawers();

                return true;
            }
        };
        navigationView.setNavigationItemSelectedListener(listener);
    }

    /* Options item selection */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
