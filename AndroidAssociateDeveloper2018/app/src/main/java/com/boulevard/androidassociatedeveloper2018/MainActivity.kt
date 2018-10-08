package com.boulevard.androidassociatedeveloper2018

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ShareCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

/**
 * General activity lifecycle notes:
 *
 * Sequence of lifecycle events after device rotation:
 * onPause, onStop, onDestroy, onCreate, onStart, onResume
 *
 */
class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Retrieve saved out state
        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("currentCount")
            counter_textview.text = count.toString()
        } else {
            counter_textview.text = "0"
        }

        /**
         * Setup custom toolbar (used for launching navigation drawer)
         */
        setSupportActionBar(main_toolbar)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_hamburger_menu)
        }

        /**
         * Navigation Drawer stuff
         */
        val navigationView: NavigationView = findViewById(R.id.navigation_drawer_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawer_layout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            nav_drawer_selection_textview.text = menuItem.title.toString()

            true
        }

        /**
         * Shared pref setup
         */
        setupSharedPreferences()
    }

    /**
     * Bound within MainActivity in share_text_button's onClick
     *
     * (Notice how the view is passed in as an argument)
     */
    fun shareText(view: View) {

        /* Implicit intent that shares text */
        ShareCompat.IntentBuilder
                .from(this)  // information about the calling activity
                .setType("text/plain")  // mime type for the data
                .setChooserTitle("Share this text with: ")  //title for the app chooser
                .setText(sample_edit_text.text.toString())  // intent data
                .startChooser()  // send the intent
    }

    /**
     * Bound within MainActivity in decrement_counter_button's onClick
     */
    fun incrementCounter(view : View) {
        count++
        counter_textview.text = count.toString()
    }

    /**
     * Bound within MainActivity in decrement_counter_button's onClick
     */
    fun decrementCounter(view : View) {
        count--
        counter_textview.text = count.toString()
    }

    fun launchPreferencesActivity(view : View) {
        val intent = Intent(this, PreferencesActivity::class.java)
        startActivity(intent)
    }

    /**
     * Custom toolbar hamburger icon launches drawer
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Somehow this id correlates to hamburger icon in custom toolbar...
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * The activity is about to become visible.
     */
    override fun onStart() {
        super.onStart()
    }

    /**
     * The activity has become visible (it is now "resumed").
     */
    override fun onResume() {
        super.onResume()
    }

    /**
     * Another activity is taking focus (this activity is about to be "paused").
     */
    override fun onPause() {
        super.onPause()
    }

    /**
     * The activity is no longer visible (it is now "stopped")
     */
    override fun onStop() {
        super.onStop()
    }

    /**
     * Activity instance reclaimed by system. Cases where this happens:
     *
     *  1. The user navigates back to the previous Activity.
     *  2. You call finish() in your Activity to manually shut it down.
     */
    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * The activity is about to be restarted.
     */
    override fun onRestart() {
        super.onRestart()
        // The activity is about to be restarted.
    }

    /**
     * Called when the user is leaving the activity (sometime before onStop())
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        // Save out state (if complete object, just use parcelable)
        outState?.putInt("currentCount", count)
    }

    /* Shared preferences stuff */
    private fun setupSharedPreferences() {

        // Get all of the values from shared preferences to set it up
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val incrementPref : Boolean = sharedPreferences.getBoolean(getString(R.string.IncrementPrefKey), false)

        if (incrementPref) {
            decrement_counter_button.visibility = View.GONE
        } else {
            decrement_counter_button.visibility = View.VISIBLE
        }

        // Register the listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == getString(R.string.IncrementPrefKey)) {

            val incrementPref : Boolean = sharedPreferences.getBoolean(getString(R.string.IncrementPrefKey), false)
            if (incrementPref) {
                decrement_counter_button.visibility = View.GONE
            } else {
                decrement_counter_button.visibility = View.VISIBLE
            }
        }
    }
}