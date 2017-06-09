package com.mjb.projectexperts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by mm on 03/05/2017.
 */
public class GridViewImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> listPhoto = new ArrayList<String>();

    /** Constructor de clase */
    public GridViewImageAdapter(Context c,ArrayList<String> images){
        mContext = c;
        listPhoto = images;
    }

    @Override
    public int getCount() {
        return listPhoto.size();
    }

    @Override
    public Object getItem(int position) {
        return listPhoto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewgroup) {
        ImageView imageView = new ImageView(mContext);
        try {
            Glide.with(mContext).load(listPhoto.get(position)).into(imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setScaleType( ImageView.ScaleType.CENTER_CROP );
        imageView.setLayoutParams( new GridView.LayoutParams(180, 220) );

        return imageView;
    }

}