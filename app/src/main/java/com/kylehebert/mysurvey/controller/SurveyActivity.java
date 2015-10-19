package com.kylehebert.mysurvey.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kylehebert.mysurvey.R;
import com.kylehebert.mysurvey.model.Survey;

import org.json.JSONException;

import java.io.IOException;

public class SurveyActivity extends AppCompatActivity {

    private TextView mSurveyQuestion;
    private Button mOption1Button;
    private Button mOption2Button;
    private Button mResultsButton;
    private Button mLaunchSurveyMakerButton;

    private int mOption1Votes = 0;
    private int mOption2Votes = 0;

    private Survey mSurvey;

    private Context mAppContext = this;
    private static final String FILENAME = "survey.json";
    private SurveyJSONSerializer mJSONSerializer = new SurveyJSONSerializer(mAppContext, FILENAME);


    private final static String EXTRA_SURVEY_AS_JSON = "com.kylehebert.mysurvey.survey_as_json";

    private final static int REQUEST_CODE_SURVEY_CREATED = 0;
    private final static int REQUEST_CODE_RESULTS_RESET = 1;


    public static Intent resetIntent(Context context){
        return new Intent(context,SurveyActivity.class);
    }


    public static Intent getNewSurveyIntent(Context context, String surveyAsJson){
        Intent intent = new Intent(context, SurveyActivity.class);
        intent.putExtra(EXTRA_SURVEY_AS_JSON, surveyAsJson);
        return intent;
    }

    public void sendVotesToResults(){
        String option1label = (String) mOption1Button.getText();
        String option2label= (String) mOption2Button.getText();
        Intent intent = ResultsActivity.surveyResultsIntent(SurveyActivity.this, mOption1Votes, mOption2Votes,
                    option1label, option2label);
        startActivityForResult(intent, REQUEST_CODE_RESULTS_RESET);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);


        mSurvey = new Survey(getString(R.string.default_survey_question),
                getString(R.string.default_option1),
                getString(R.string.default_option2));

        mSurveyQuestion = (TextView)findViewById(R.id.questionTextView);
        mSurveyQuestion.setText(mSurvey.getSurveyQuestion());

        mOption1Button = (Button)findViewById(R.id.option1Button);
        mOption1Button.setText(mSurvey.getOption1());
        mOption1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //increment option1 counter
                mOption1Votes++;
            }
        });

        mOption2Button = (Button)findViewById(R.id.option2Button);
        mOption2Button.setText(mSurvey.getOption2());
        mOption2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //increment option2 counter
                mOption2Votes++;
            }
        });

        mResultsButton = (Button)findViewById(R.id.resultsButton);
        mResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass votes to ResultsActivity

                sendVotesToResults();
            }
        });

        mLaunchSurveyMakerButton = (Button)findViewById(R.id.launchSurveyMakerButton);
        mLaunchSurveyMakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyActivity.this, CreateSurveyActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SURVEY_CREATED);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_CODE_SURVEY_CREATED){
            if (data == null) {
                return;
            }
            //deserialize the JSON string back to a Survey object
            String surveyAsJson = data.getStringExtra(EXTRA_SURVEY_AS_JSON);
            Survey survey = Survey.surveyFromJson(surveyAsJson);

            //replace the default survey strings with strings from the new Survey object
            mSurveyQuestion.setText(survey.getSurveyQuestion());
            mOption1Button.setText(survey.getOption1());
            mOption2Button.setText(survey.getOption2());
        }

        if (requestCode == REQUEST_CODE_RESULTS_RESET) {
            if (data == null) {
                return;
            }

            mOption1Votes = 0;
            mOption2Votes = 0;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            mJSONSerializer.saveSurvey(mSurvey);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey, menu);
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
