package com.example.amit.popularmovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by amit on 9/27/2015.
 */
public class MovieDetailFragment extends Fragment {

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detailView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        if (intent != null) {
            double rating = bundle.getDouble("ratings");
            String title = bundle.getString("title");
           // String ratings = String.format(" (%.1f/10)", rating);
            ((TextView) detailView.findViewById(R.id.ratings)).setText(String.format(" (%.1f/10)", rating));
            ((TextView) detailView.findViewById(R.id.title)).setText(bundle.getString("title"));
            ((TextView) detailView.findViewById(R.id.date)).setText(bundle.getString("release_date"));
            ((TextView) detailView.findViewById(R.id.plot)).setText(bundle.getString("plot"));

            String imageURLStr = bundle.getString("poster_URL");

            // Use Picasso to cache load the images for movies
            try {
                ImageView imageView = ((ImageView) detailView.findViewById(R.id.poster));
                Picasso.with(detailView.getContext()).load(imageURLStr).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return detailView;
    }
}
