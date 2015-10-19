package com.kylehebert.mysurvey.controller;

import android.content.Context;

import com.kylehebert.mysurvey.model.Survey;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by kylehebert on 10/10/15.
 * Used to convert Survey objects to JSON using the GSON library
 *
 * I used the Gson library to serialize Survey objects when I
 * initially wrote the app, and continued to use them here
 * when saving the survey to disk
 */
public class SurveyJSONSerializer {

    private Context mContext;
    private String mFileName;

    public SurveyJSONSerializer(Context context, String fileName) {
        mContext = context;
        mFileName = fileName;
    }



    public Survey loadSurvey() throws IOException, JSONException {
        Survey survey = null;
        BufferedReader bufferedReader = null;
        try {
            //open and read the file into a Stringbuilder
            InputStream inputStream = mContext.openFileInput(mFileName);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                survey = Survey.surveyFromJson(line);

            }
        } catch (FileNotFoundException fnf) {

        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
            return survey;
    }

    public void saveSurvey(Survey survey)
            throws JSONException, IOException {

        //convert survey to JSON
        Survey.surveyToJson(survey);


        //write the survey to disk
        Writer writer = null;
        try {
            OutputStream outputStream = mContext.openFileOutput(mFileName,Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(survey.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }





}
