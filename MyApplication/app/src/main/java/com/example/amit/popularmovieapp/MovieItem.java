package com.example.amit.popularmovieapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;

/**
 * Created by amit on 9/26/2015.
 */
public class MovieItem {
    final String PMA_RESULT = "results";

    public MovieItem() {
    }

    String LOG_TAG = MovieItem.class.getSimpleName();

    public String getTitle(String JsonData, int position) {

        final String PMA_TITLE = "original_title";

        String title = "";
        try {
            JSONObject rootObject = new JSONObject(JsonData);
            JSONArray resultArray = rootObject.optJSONArray(PMA_RESULT);
            if (position < resultArray.length()) {
                JSONObject movie = resultArray.getJSONObject(position);
                title = movie.getString(PMA_TITLE);
            }

        } catch (Exception e) {
            Log.d(LOG_TAG, "Error fetching JSOn" + JsonData);
            e.printStackTrace();
        }
        return title;
    }

    /* public String getPoster(String JsonData, int position){
          final String
    }
  */
    public String getDate(String JsonData, int position) {
        final String PMA_DATE = "release_date";
        String date = "";
        try {
            JSONObject rootObject = new JSONObject(JsonData);
            JSONArray resultArray = rootObject.optJSONArray(PMA_RESULT);
            if (position < resultArray.length()) {
                JSONObject movie = resultArray.getJSONObject(position);
                date = movie.getString(PMA_DATE);

            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error" + date);
            e.printStackTrace();
        }
        return date;
    }

    public String getPlot(String JsonData, int position) {
        final String PMA_PLOT = "overview";
        String plot = "";
        try {
            JSONObject rootObject = new JSONObject(JsonData);
            JSONArray resultArray = rootObject.optJSONArray(PMA_RESULT);
            if (position < resultArray.length()) {
                JSONObject movie = resultArray.getJSONObject(position);
                plot = movie.getString(PMA_PLOT);

            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error" + plot);
            e.printStackTrace();
        }
        return plot;
    }

    public double getRatings(String JsonData, int position) {
        final String PMA_RATINGS = "vote_average";
        double ratings = 0;
        try {
            JSONObject rootObject = new JSONObject(JsonData);
            JSONArray resultArray = rootObject.optJSONArray(PMA_RESULT);
            if (position < resultArray.length()) {
                JSONObject movie = resultArray.getJSONObject(position);
                ratings = movie.getDouble(PMA_RATINGS);

            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error" + ratings);
            e.printStackTrace();
        }
        return ratings;
    }

}

