package com.example.amit.popularmovieapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amit on 9/25/2015.
 */
public class MovieAdapter extends BaseAdapter {
    String LOG_TAG = MovieAdapter.class.getSimpleName();
    Context c;
    ArrayList<String> moviePoster;
    LayoutInflater mInflater;

    public MovieAdapter(Context c, ArrayList moviePoster) {
        this.c = c;
        this.moviePoster = moviePoster;
    }


    public long getItemId(int position) {
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public int getCount() {
        return moviePoster.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView moviePoster;

        if (convertView == null) {
            moviePoster = new ImageView(c);
        //    moviePoster.setLayoutParams(new GridView.LayoutParams(85, 85));
            //moviePoster.setScaleType(ImageView.ScaleType.CENTER_CROP);
           moviePoster.setPadding(4, 4, 4, 4);
        } else {
            moviePoster = (ImageView) convertView;
        }

        String posterString = getPoster(position);

        try {
            Picasso.with(c).load(posterString).into(moviePoster);
            Log.d(LOG_TAG, "aaaa" + moviePoster);
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error Loading image" + moviePoster);
            e.printStackTrace();

        }
        return moviePoster;


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


