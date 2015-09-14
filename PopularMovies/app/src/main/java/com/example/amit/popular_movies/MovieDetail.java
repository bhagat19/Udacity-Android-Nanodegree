package com.example.amit.popular_movies;

import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by amit on 9/13/2015.
 */
public class MovieDetail extends AppCompatActivity {
    private TextView titleTextView;
    private ImageView thumbnail;
    private TextView plotTextView;
    private String movieName;
    private String poster;
    private RatingBar ratings;
    private String plot;

    public MovieDetail(){
       super();
    }

    public String getMovie(){
      return movieName;
    }
    public String getPoster(){
        return poster;
    }
    public String getPlot(){
        return plot;
    }
    public void setPlot(String plot){
        this.plot = plot;
    }
   // public Rating getRatings(){
     //   return ratings.getRating();
   // }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        //String plot= getIntent().getStringExtra("Plot");
       // titleTextView = (TextView) findViewById(R.id.title);
        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        //plotTextView = (TextView) findViewById(R.id.plot);

       // plotTextView.setText(Html.fromHtml(plot));
        // titleTextView.setText(Html.fromHtml(title));


       Picasso.with(this).load(image).into(thumbnail);
    }
}

