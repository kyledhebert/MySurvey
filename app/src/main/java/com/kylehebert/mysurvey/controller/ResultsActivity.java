package com.kylehebert.mysurvey.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kylehebert.mysurvey.R;

public class ResultsActivity extends AppCompatActivity {

    private TextView mOption1Label;
    private TextView mOption2Label;
    private TextView mOption1Results;
    private TextView mOption2Results;
    private Button mResetButton;
    private Button mContinueButton;

    private static final String EXTRA_OPTION_ONE_LABEL = "com.kylehebert.mysurvey.option_one_label";
    private static final String EXTRA_OPTION_TWO_LABEL = "com.kylehebert.mysurvey.option_two_label";
    private static final String EXTRA_OPTION_ONE_VOTES = "com.kylehebert.mysurvey.option_one_votes";
    private static final String EXTRA_OPTION_TWO_VOTES = "com.kylehebdert.mysurvey.option_two_votes";


    /*
    This method will be called when collecting results for a survey.
    We need to know the options as well as the results to display them.
    This we we don't have to worry if we are viewing results for the default survey
    or a user created survey.
     */
    public static Intent surveyResultsIntent(Context context, int option1Votes, int option2Votes,
                                             String option1Label, String option2label){
        Intent intent = new Intent(context, ResultsActivity.class);
        intent.putExtra(EXTRA_OPTION_ONE_VOTES,option1Votes);
        intent.putExtra(EXTRA_OPTION_TWO_VOTES,option2Votes);
        intent.putExtra(EXTRA_OPTION_ONE_LABEL,option1Label);
        intent.putExtra(EXTRA_OPTION_TWO_LABEL,option2label);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mOption1Label = (TextView)findViewById(R.id.option1Label);
        mOption1Label.setText(getIntent().getStringExtra(EXTRA_OPTION_ONE_LABEL) + ": ");

        mOption1Results = (TextView)findViewById(R.id.option1Results);
        mOption1Results.setText(Integer.toString(getIntent().getIntExtra(EXTRA_OPTION_ONE_VOTES, 0)));

        mOption2Label = (TextView)findViewById(R.id.option2Label);
        mOption2Label.setText(getIntent().getStringExtra(EXTRA_OPTION_TWO_LABEL) + ": ");

        mOption2Results = (TextView)findViewById(R.id.option2Results);
        mOption2Results.setText(Integer.toString(getIntent().getIntExtra(EXTRA_OPTION_TWO_VOTES, 0)));

        mResetButton = (Button)findViewById(R.id.resetButton);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SurveyActivity.resetIntent(ResultsActivity.this);
                setResult(RESULT_OK,intent);
                finish();

            }
        });

        mContinueButton = (Button)findViewById(R.id.continueButton);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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
