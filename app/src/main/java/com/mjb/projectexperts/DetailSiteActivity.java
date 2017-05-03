package com.mjb.projectexperts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class DetailSiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_site);


        GridView gridView = (GridView) findViewById( R.id.GridView1 );
        gridView.setAdapter( new GridViewImageAdapter(this) );
    }
}
