package com.mjb.projectexperts;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import static com.mjb.projectexperts.R.id.map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        Button btnView = (Button) findViewById(R.id.btn_view_sites);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(MapActivity.this, DetailSiteActivity.class);
                startActivity(main);
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);


        String[] sitios = {"Sitio 1","Sitio 2","Sitio 3"};
        ArrayAdapter<String> arrayAdapterSite = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,sitios );
        MaterialBetterSpinner materialDesignSpinnerSite = (MaterialBetterSpinner)
                findViewById(R.id.spinner_site);
        materialDesignSpinnerSite.setAdapter(arrayAdapterSite);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);

        }


    }
}
