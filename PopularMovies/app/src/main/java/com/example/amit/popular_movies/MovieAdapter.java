package com.example.amit.popular_movies;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Amit on 9/4/2015.
 */
public class MovieAdapter extends ArrayAdapter<GridItem> {

    private Context mContext;
    private int layoutResourceID;
    private ArrayList<GridItem> mData = new ArrayList<GridItem>();

    public MovieAdapter(Context mContext, int layoutResourceID,ArrayList<GridItem> mData)
    {
        super(mContext,layoutResourceID,mData);
        this.mContext= mContext;
        this.layoutResourceID = layoutResourceID;
        this.mData = mData;
    }
    public void setGridData(ArrayList<GridItem> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        View row = convertView;
        ViewHolder holder;
        if(row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceID, parent, false);
            holder = new ViewHolder();
            holder.titleText = (TextView) row.findViewById(R.id.grid_item_text);
            holder.image = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        }

            else{
               holder = (ViewHolder) row.getTag();
            }

        GridItem item = mData.get(position);
        holder.titleText.setText(Html.fromHtml(item.getTitle()));
        Picasso.with(mContext).load(item.getImage()).into(holder.image);
        return row;
        }

    public class ViewHolder{
        TextView titleText;
        ImageView image;
    }
}
