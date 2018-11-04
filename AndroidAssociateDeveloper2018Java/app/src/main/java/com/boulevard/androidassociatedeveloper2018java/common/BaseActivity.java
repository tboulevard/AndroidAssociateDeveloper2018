package com.boulevard.androidassociatedeveloper2018java.common;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.boulevard.androidassociatedeveloper2018java.R;
import com.boulevard.androidassociatedeveloper2018java.boardingpass.BoardingPassActivity;
import com.boulevard.androidassociatedeveloper2018java.common.util.PreferenceUtil;
import com.boulevard.androidassociatedeveloper2018java.contentprovider.ContentProviderActivity;
import com.boulevard.androidassociatedeveloper2018java.jobscheduler.JobSchedulerFragment;
import com.boulevard.androidassociatedeveloper2018java.settings.SettingsActivity;
import com.boulevard.androidassociatedeveloper2018java.todolist.ListViewFragment;


public class BaseActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    DrawerLayout drawerLayout;
    Toolbar mainToolbar;
    FrameLayout fragmentContainer;

    private JobSchedulerFragment jobSchedulerFragment = null;
    private ListViewFragment listViewFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Setup view elements for reference, after view is created
        drawerLayout = findViewById(R.id.drawer_layout);
        mainToolbar = findViewById(R.id.main_toolbar);
        fragmentContainer = findViewById(R.id.fragment_container);

        /*
         * Setup custom toolbar (used for launching navigation drawer)
         */
        Boolean showActionBar = PreferenceUtil.getSharedPreferences(this)
                .getBoolean(getString(R.string.pref_show_action_bar), true);

        setSupportActionBar(mainToolbar);

        if (showActionBar) {
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
            actionbar.setTitle("Home");
        } else {
            getSupportActionBar().setTitle("Disabled");
        }

        /* Auto populate with base fragment */
        /* TODO: Ignore this for screen rotation */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

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

                if (menuItem.getItemId() == R.id.nav_list_view) {

                    if (listViewFragment == null) {
                        listViewFragment = new ListViewFragment();
                    }
                    getSupportActionBar().setTitle("TODO List");
                    fragmentTransaction.replace(R.id.fragment_container, listViewFragment, "listview_fragment_tag");
                    fragmentTransaction.commit();

                } else if (menuItem.getItemId() == R.id.nav_job_scheduler) {

                    if (jobSchedulerFragment == null) {
                        jobSchedulerFragment = new JobSchedulerFragment();

                    }
                    getSupportActionBar().setTitle("Job Scheduler");
                    fragmentTransaction.replace(R.id.fragment_container, jobSchedulerFragment, "jobscheduler_fragment_tag");
                    fragmentTransaction.commit();

                } else if (menuItem.getItemId() == R.id.nav_boarding_pass) {

                    Intent boardingPassIntent = new Intent(BaseActivity.this, BoardingPassActivity.class);
                    startActivity(boardingPassIntent);
                }

                drawerLayout.closeDrawers();

                return true;
            }
        };
        navigationView.setNavigationItemSelectedListener(listener);

        // Register onSharedPreferenceChangeListener
        PreferenceUtil.getSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceUtil.getSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    /* Options item selection */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        } else if (item.getItemId() == R.id.action_settings) {
            Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsActivityIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * For settings menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_show_action_bar))) {
            Boolean showActionBar = sharedPreferences.getBoolean(key, true);
            if (showActionBar) {
                setSupportActionBar(mainToolbar);
                ActionBar actionbar = getSupportActionBar();
                actionbar.setDisplayHomeAsUpEnabled(true);
                actionbar.setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
                actionbar.setTitle("Home");
            } else {
                getSupportActionBar().setTitle("Disabled");
            }
        }
    }

    public void launchContentProviderActivity(View view) {
        Intent contentProviderActivityIntent = new Intent(this, ContentProviderActivity.class);
        startActivity(contentProviderActivityIntent);
    }
}
