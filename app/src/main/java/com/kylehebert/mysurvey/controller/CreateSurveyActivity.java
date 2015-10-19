package com.kylehebert.mysurvey.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kylehebert.mysurvey.R;
import com.kylehebert.mysurvey.model.Survey;

public class CreateSurveyActivity extends AppCompatActivity {

    private EditText mQuestionEditText;
    private EditText mOption1EditText;
    private EditText mOption2EditText;
    private Button mCreateSurveyButton;

    private final static String EXTRA_SURVEY_CREATED = "com.kylehebert.mysurvey.survey_is_created";
    private final static String EXTRA_SURVEY_AS_JSON= "com.kylehebert.mysurvey.survey_as_json";


    private Survey createNewSurvey(){
        String question = mQuestionEditText.getText().toString();
        String option1 = mOption1EditText.getText().toString();
        String option2= mOption2EditText.getText().toString();

        return new Survey(question,option1,option2);
    }

    private Intent prepareNewSurveyResult(String surveyAsJson){
        Intent intent = SurveyActivity.getNewSurveyIntent(CreateSurveyActivity.this,surveyAsJson);
        intent.putExtra(EXTRA_SURVEY_CREATED,true);
        intent.putExtra(EXTRA_SURVEY_AS_JSON,surveyAsJson);
        setResult(RESULT_OK, intent);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);

        mQuestionEditText = (EditText)findViewById(R.id.survey_question_edit_text);

        mOption1EditText = (EditText)findViewById(R.id.option_one_edit_text);

        mOption2EditText = (EditText)findViewById(R.id.option_two_edit_text);

        mCreateSurveyButton = (Button)findViewById(R.id.createSurveyButton);
        mCreateSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a new survey object
                Survey survey = createNewSurvey();

                //convert survey object to json so that it can be passed as an intent
                //Android docs recommend using JSON over serialization (see Survey.java for more details)
                String surveyAsJson = Survey.surveyToJson(survey);

                //set survey created result and send back to Survey Activity
                prepareNewSurveyResult(surveyAsJson);

                finish();

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_survey, menu);
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
