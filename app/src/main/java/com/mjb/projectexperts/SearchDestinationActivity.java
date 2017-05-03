package com.mjb.projectexperts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SearchDestinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_destination);


        Spinner spinnerL = (Spinner) findViewById(R.id.spinner_location);
        Spinner spinnerTA = (Spinner) findViewById(R.id.spinner_typeActivity);
        Spinner spinnerTT = (Spinner) findViewById(R.id.spinner_typeTourism);
        String[] location = {"San José","Cartago","Heredia","Alajuela","Puntarenas","Limón", "Guanacaste"};
        String[] activity = {"Actividad 1","Actividad 2"};
        String[] tourism = {"Turismo 1","Turismo 2"};
        spinnerL.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, location));
        spinnerTA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, activity));
        spinnerTT.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tourism));


        Button btnAccept = (Button)findViewById(R.id.btn_accept_search);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(SearchDestinationActivity.this, RoutesFoundActivity.class);
                startActivity(main);
                //finish();
            }
        });

        Button btnRegistry = (Button)findViewById(R.id. btn_registry_search);
        btnRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(SearchDestinationActivity.this, LoginActivity.class);
                startActivity(main);
                //finish();
            }
        });



    }
}
