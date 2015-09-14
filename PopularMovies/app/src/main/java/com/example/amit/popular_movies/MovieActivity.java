package com.example.amit.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by amit on 9/4/2015.
 */
public class MovieActivity extends AppCompatActivity {

    private Context mContext;
    private MovieAdapter mMovieAdapter;
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private ArrayList<GridItem> mData = new ArrayList<GridItem>();
  //  private ArrayList<MovieDetail> movieDetails = new ArrayList<MovieDetail>();
   private String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=f38b1a07ccfc6e604a89bf88d7596032";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.gridview);
        mProgressBar = (ProgressBar) findViewById(R.id.progessBar);

        //initialize the adapter
        mData = new ArrayList<GridItem>();
        mMovieAdapter = new MovieAdapter(this, R.layout.griditem, mData);
        mGridView.setAdapter(mMovieAdapter);

        mGridView.setOnItemClickListener(new GridView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                GridItem item = (GridItem) mGridView.getItemAtPosition(position);
               // MovieDetail dItem = new MovieDetail();

                Intent intent = new Intent(MovieActivity.this,MovieDetail.class);
                intent.putExtra("title",item.getTitle());
                intent.putExtra("Image",item.getImage());
             //   intent.putExtra("Plot",dItem.getPlot());

                startActivity(intent);
            }});

        new FetchMovieTask().execute(URL);
        mProgressBar.setVisibility(View.VISIBLE);
    }


    //Async task to download data
    public class FetchMovieTask extends AsyncTask <String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String ...params){
            boolean result = false;
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=f38b1a07ccfc6e604a89bf88d7596032");
               // Log.v("API data","Json",url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                if(urlConnection.getResponseCode() == 200) {
                    String response = streamToString(in);
                    parseResult(response);
                    result = true;
                }
                else
                result = false;

            }
                catch(IOException e) {
                    Log.v("Connection","Error",e);
                }

            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        return result;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            // update UI upon data download

            if (result) {
                mMovieAdapter.setGridData(mData);
            } else {
                Toast.makeText(mContext, "Failed to fetch data!", Toast.LENGTH_SHORT).show();

           }

            //Hide progressbar
            mProgressBar.setVisibility(View.GONE);
        }
    }


public String streamToString(InputStream inputStream) throws IOException{
String result = "";
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    String line;
    while((line = reader.readLine()) != null ) {
       result = result + line;
        Log.v("API Data",result);
    }
    if(inputStream != null) {
        inputStream.close();
    }
    return result;
    }
public void parseResult(String response){
    String base_url =  "http://image.tmdb.org/t/p/w185/";
    try{
        GridItem item = null;
        MovieDetail dItem = null;
       JSONObject root = new JSONObject(response);
        JSONArray result = root.optJSONArray("results");

        for(int i =0; i<result.length(); i++) {
        //    if(result != JSONObject.NULL) {
                JSONObject data = result.optJSONObject(i);
                String title = data.optString("original_title");
               // String plot = data.optString("overview");
                //dItem = new MovieDetail();
                //dItem.setPlot(plot);
                item = new GridItem();
                item.setTitle(title);
               // String url = base_url + "w185/" + data.optString("poster_path");
                item.setImage(base_url + data.getString("poster_path"));

               // item.setImage(Picasso.with(this).load(url).toString());
            mData.add(item);
           // movieDetails.add(dItem);
        }
       // }
       // mData.add(item);

    }
    catch(JSONException e){
        e.printStackTrace();
    }


}
}


