package com.kylehebert.mysurvey.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kylehebert.mysurvey.controller.SurveyJSONSerializer;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by kylehebert on 9/3/15. Objects of this class will represent
 * a single survey containing one question and two answers.
 */
public class Survey {

    private String mSurveyQuestion;
    private String mOption1;
    private String mOption2;
    private int mOption1Votes;
    private int mOption2Votes;

    private static final int INITIAL_VOTES = 0;

    private SurveyJSONSerializer mJSONSerializer;

    private final static String TAG = "MySurvey";

    private static Survey sSurvey;
    private Context mAppContext;


    public Survey(String surveyQuestion, String choice1, String choice2) {
        mSurveyQuestion = surveyQuestion;
        mOption1 = choice1;
        mOption2 = choice2;
        mOption1Votes = INITIAL_VOTES;
        mOption2Votes = INITIAL_VOTES;
    }

    public boolean saveSurvey(Survey survey) {

        try {
            mJSONSerializer.saveSurvey(survey);
            Log.d(TAG, "survey saved");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "error saving survey");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * I decided to convert my Survey objects to JSON using the Gson library after reading the
     * Android docs on Serializable, in which they recommend an alternative like Gson.
     * The code below is based on the Gson user guide found here:
     * https://sites.google.com/site/gson/gson-user-guide
     */

    //this method will convert the Survey object to a JSON string that we can pass
    //as an extra to the SurveyActivity via an intent
    public static String surveyToJson(Survey survey){
        Gson gson = new Gson();
        return gson.toJson(survey);

    }

    //this method will convert a JSON string back to a Survey object
    public static Survey surveyFromJson(String surveyAsJson){
        Gson gson = new Gson();
        return gson.fromJson(surveyAsJson,Survey.class);
    }



    public String getSurveyQuestion() {
        return mSurveyQuestion;
    }

    public void setSurveyQuestion(String surveyQuestion) {
        mSurveyQuestion = surveyQuestion;
    }

    public String getOption1() {
        return mOption1;
    }

    public void setOption1(String option1) {
        mOption1 = option1;
    }

    public String getOption2() {
        return mOption2;
    }

    public void setOption2(String option2) {
        mOption2 = option2;
    }

    public int getOption1Votes() {
        return mOption1Votes;
    }

    public void setOption1Votes(int option1Votes) {
        mOption1Votes = option1Votes;
    }

    public int getOption2Votes() {
        return mOption2Votes;
    }

    public void setOption2Votes(int option2Votes) {
        mOption2Votes = option2Votes;
    }




}
