package com.boulevard.androidassociatedeveloper2018java.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.boulevard.androidassociatedeveloper2018java.R;
import com.udacity.example.droidtermsprovider.DroidTermsExampleContract;

public class ContentProviderActivity extends AppCompatActivity {

    // COMPLETED (3) Create an instance variable storing a Cursor called mData
    // The data from the DroidTermsExample content provider
    private Cursor mData;

    // The index of the definition and word column in the cursor
    private int mDefColumn, mWordColumn;

    private TextView mWordTextView, mDefinitionTextView;

    // The current state of the app
    private int mCurrentState;

    private Button mButton;

    // This state is when the word definition is hidden and clicking the button will therefore
    // show the definition
    private final int STATE_HIDDEN = 0;

    // This state is when the word definition is shown and clicking the button will therefore
    // advance the app to the next word
    private final int STATE_SHOWN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        // Get the views
        // COMPLETED (1) You'll probably want more than just the Button
        mWordTextView = (TextView) findViewById(R.id.text_view_word);
        mDefinitionTextView = (TextView) findViewById(R.id.text_view_definition);
        mButton = (Button) findViewById(R.id.button_next);

        //Run the database operation to get the cursor off of the main thread
        // COMPLETED (5) Create and execute your AsyncTask here
        new WordFetchTask().execute();

    }

    /**
     * This is called from the layout when the button is clicked and switches between the
     * two app states.
     *
     * @param view The view that was clicked
     */
    public void onButtonClick(View view) {

        // Either show the definition of the current word, or if the definition is currently
        // showing, move to the next word.
        switch (mCurrentState) {
            case STATE_HIDDEN:
                showDefinition();
                break;
            case STATE_SHOWN:
                nextWord();
                break;
        }
    }

    public void nextWord() {

        // COMPLETED (3) Go to the next word in the Cursor, show the next word and hide the definition
        // Note that you shouldn't try to do this if the cursor hasn't been set yet.
        // If you reach the end of the list of words, you should start at the beginning again.

        if (mData != null) {
            // Move to the next position in the cursor, if there isn't one, move to the first
            if (!mData.moveToNext()) {
                mData.moveToFirst();
            }
            // Hide the definition TextView
            mDefinitionTextView.setVisibility(View.INVISIBLE);

            // Change button text
            mButton.setText(getString(R.string.show_definition));

            // Get the next word
            mWordTextView.setText(mData.getString(mWordColumn));
            mDefinitionTextView.setText(mData.getString(mDefColumn));

            mCurrentState = STATE_HIDDEN;
        }
    }

    public void showDefinition() {

        // COMPLETED (4) Show the definition
        if (mData != null) {
            // Show the definition TextView
            mDefinitionTextView.setVisibility(View.VISIBLE);

            // Change button text
            mButton.setText(getString(R.string.next_word));

            mCurrentState = STATE_SHOWN;
        }
    }

    // COMPLETED (1) Create AsyncTask with the following generic types <Void, Void, Cursor>
    // COMPLETED (2) In the doInBackground method, write the code to access the DroidTermsExample
    // provider and return the Cursor object
    // COMPLETED (4) In the onPostExecute method, store the Cursor object in mData

    // Use an async task to do the data fetch off of the main thread.
    public class WordFetchTask extends AsyncTask<Void, Void, Cursor> {

        // Invoked on a background thread
        @Override
        protected Cursor doInBackground(Void... params) {
            // Make the query to get the data

            // Get the content resolver
            ContentResolver resolver = getContentResolver();

            // Call the query method on the resolver with the correct Uri from the contract class
            Cursor cursor = resolver.query(DroidTermsExampleContract.CONTENT_URI,
                    null, null, null, null);
            return cursor;
        }


        // Invoked on UI thread
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            // Set the data for MainActivity
            mData = cursor;

            mWordColumn = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_WORD);
            mDefColumn = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_DEFINITION);

            // Set the initial state
            nextWord();
        }
    }
}
