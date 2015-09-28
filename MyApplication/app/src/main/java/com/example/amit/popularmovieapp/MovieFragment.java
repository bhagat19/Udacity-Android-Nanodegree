package com.example.amit.popularmovieapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by amit on 9/25/2015.
 */
public class MovieFragment extends Fragment {
    Context mContext;
    MovieAdapter mMovieAdapter;
    ArrayList<String> moviePoster;
    String mSort;
    String movieJsonStr;
     MovieItem item = new MovieItem();


    public MovieFragment(){moviePoster = new ArrayList<>();}
    String LOG_TAG = MovieFragment.class.getSimpleName();

   @Override
   public void onSaveInstanceState( Bundle out){
    out.putStringArrayList("posters", moviePoster);
       out.putString("movies", movieJsonStr);
       out.putString("sortOrder",mSort);
       super.onSaveInstanceState(out);
   }

    @Override
    public void onStart(){
    super.onStart();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortby = sharedPrefs.getString(
                getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_popular));

        if (!sortby.equals(mSort)) {
            // sort order has changed, need to re-get the movie data from server
            FetchMovieTask fetchMovieTask = new FetchMovieTask();
            fetchMovieTask.execute();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstance){

        View rootView = inflater.inflate(R.layout.fragment_view,container,false);
        GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
        mMovieAdapter = new MovieAdapter(getActivity(),moviePoster);
        gridview.setAdapter(mMovieAdapter);
        gridview.setClickable(true);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                    intent.putExtra("title", item.getTitle(movieJsonStr,position));
                    intent.putExtra("plot",item.getPlot(movieJsonStr, position));
                    intent.putExtra("release_date",item.getDate(movieJsonStr, position));
                    intent.putExtra("ratings",item.getRatings(movieJsonStr, position));
                    intent.putExtra("poster_URL",getPoster(position));
                    startActivity(intent);
            }catch (Exception e) {
                    Log.d(LOG_TAG, "Error" + movieJsonStr);
                    e.printStackTrace();
                }
        }




    });
        if(savedInstance == null){
          FetchMovieTask fetchMovieTask = new FetchMovieTask();
            fetchMovieTask.execute();}
            else{
                moviePoster = savedInstance.getStringArrayList("posters");
                movieJsonStr = savedInstance.getString("movies");
            }
    return rootView;
    }





    public class FetchMovieTask extends AsyncTask<Void,Void,String[]> {

    final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    @Override
    protected String[] doInBackground(Void... Nothing){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String line;
        StringBuffer buffer = new StringBuffer();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortby = sharedPrefs.getString(
                getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_popular));
        mSort = sortby;

        try{
            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("discover")
                    .appendPath("movie")
                    .appendQueryParameter("sort_by",sortby)
                    .appendQueryParameter("api_key", getActivity().getString(R.string.APIKEY))
                    .build();
            URL movieURL = new URL(uri.toString());

            urlConnection = (HttpURLConnection) movieURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if(urlConnection.getResponseCode() == 200 ){
                if (inputStream == null){
                    movieJsonStr = null;}

                    reader = new BufferedReader(new InputStreamReader(inputStream));
                while((line = reader.readLine()) != null){
                    buffer.append(line +"\n");
                }
                if (buffer.length() == 0){
                    movieJsonStr = null;
                }
                    movieJsonStr = buffer.toString();

            }



        }catch(IOException e){
            Log.d(LOG_TAG,"Error",e);
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMoviePosters(movieJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    return null;
    }
@Override
protected void onPostExecute(String[] posters) {
    if (posters != null) {
        moviePoster.clear();
        moviePoster.addAll(Arrays.asList(posters));

        mMovieAdapter.notifyDataSetChanged();
    }
    super.onPostExecute(posters);
}
}
    public String[] getMoviePosters(String string) throws JSONException{

        final String PMA_RESULTS = "results";
        final String PMA_POSTER = "poster_path";

        JSONArray movieArray = new JSONObject(movieJsonStr).getJSONArray(PMA_RESULTS);

        String[] resultStr = new String[movieArray.length()];
        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject movieJSON = movieArray.getJSONObject(i);
            resultStr[i] = movieJSON.getString(PMA_POSTER);
        }
        return resultStr;
    }
    private String getPoster(int position) {

        Uri.Builder uri = new Uri.Builder();
        uri.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendEncodedPath(moviePoster.get(position))
                .build();

        return uri.toString();
    }
}
