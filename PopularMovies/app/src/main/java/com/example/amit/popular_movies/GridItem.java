package com.example.amit.popular_movies;

/**
 * Created by amit on 9/4/2015.
 */
public class GridItem {
    private String title;
    private String image;

    public GridItem(){
    super();
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return image;
    }
}
