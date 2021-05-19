package com.streamliners.hellosharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private final String sharedPrefFile ="com.example.android.helloSharedPrefs";

    // Current count
    private int mCount = 0;
    // Current background color
    private int mColor;
    // Text view to display both count and color
    private TextView mShowCountTextView;

    // Key for current count
    private final String COUNT_KEY = "count";
    // Key for current color
    private final String COLOR_KEY = "color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views, color
        mShowCountTextView = findViewById(R.id.show_count);
        mColor = ContextCompat.getColor(this,
                R.color.default_background);

        prefs = getPreferences(MODE_PRIVATE);
        mCount = prefs.getInt(Constants.COUNT, 0);
        mColor = prefs.getInt(Constants.COLOR, mColor);
        mShowCountTextView.setText("" + mCount);
        mShowCountTextView.setBackgroundColor(mColor);
    }


    /**
     * Handles the onClick for the background color buttons. Gets background
     * color of the button that was clicked, and sets the TextView background
     * to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    public void changeBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        mShowCountTextView.setBackgroundColor(color);
        mColor = color;
    }

    /**
     * Handles the onClick for the Count button. Increments the value of the
     * mCount global and updates the TextView.
     *
     * @param view The view (Button) that was clicked.
     */
    public void countUp(View view) {
        mCount++;
        mShowCountTextView.setText(String.format("%s", mCount));
    }


    /**
     * Handles the onClick for the Reset button. Resets the global count and
     * background variables to the defaults and resets the views to those
     * default values.
     *
     * @param view The view (Button) that was clicked.
     */
    public void reset(View view) {
        // Reset count
        mCount = 0;
        mShowCountTextView.setText(String.format("%s", mCount));

        // Reset color
        mColor = ContextCompat.getColor(this,
                R.color.default_background);
        mShowCountTextView.setBackgroundColor(mColor);
        SharedPreferences.Editor preferencesEditor = prefs.edit();
        preferencesEditor.clear()
                                .apply();

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        prefs.edit()
                .putInt(Constants.COUNT, mCount)
                .putInt(Constants.COLOR, mColor)
                .apply();
    }
}
