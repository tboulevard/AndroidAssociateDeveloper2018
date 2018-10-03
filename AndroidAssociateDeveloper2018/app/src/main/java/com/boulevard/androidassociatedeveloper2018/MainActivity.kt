package com.boulevard.androidassociatedeveloper2018

import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        share_text_button.setOnClickListener {

            /* Implicit intent that shares text */
            ShareCompat.IntentBuilder
                    .from(this)  // information about the calling activity
                    .setType("text/plain")  // mime type for the data
                    .setChooserTitle("Share this text with: ")  //title for the app chooser
                    .setText("Shared text!")  // intent data
                    .startChooser()  // send the intent

        }
    }
}