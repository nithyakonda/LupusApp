package com.umb.cs682.projectlupus.activities.moodAlert;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.umb.cs682.projectlupus.R;
import com.umb.cs682.projectlupus.util.Constants;

public class MoodPopUp extends Activity {

    private Button button_sbm;
    private TextView text_v;
    private RatingBar rating_b;
    int reminderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_mood_pop_up);
        reminderID = getIntent().getIntExtra(Constants.REMINDER_ID, -1);
        listenerForRatingBar();
    }

    public void listenerForRatingBar(){
        rating_b=(RatingBar)findViewById(R.id.ratingBar);
        text_v=(TextView)findViewById(R.id.numRating);

        rating_b.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                text_v.setText(String.valueOf(rating));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
