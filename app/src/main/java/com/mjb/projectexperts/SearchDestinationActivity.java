package com.mjb.projectexperts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class SearchDestinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_destination);


        String[] location = {"San José","Cartago","Heredia","Alajuela","Puntarenas","Limón", "Guanacaste"};
        String[] activity = {"Actividad 1","Actividad 2"};
        String[] tourism = {"Turismo 1","Turismo 2"};

        ArrayAdapter<String> arrayAdapterLocation = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, location);
        MaterialBetterSpinner materialDesignSpinnerLocation = (MaterialBetterSpinner)
                findViewById(R.id.spinner_location);
        materialDesignSpinnerLocation.setAdapter(arrayAdapterLocation);

        ArrayAdapter<String> arrayAdapterActivity = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, activity);
        MaterialBetterSpinner materialDesignSpinnerActivity = (MaterialBetterSpinner)
                findViewById(R.id.spinner_typeActivity);
        materialDesignSpinnerActivity.setAdapter(arrayAdapterActivity);

        ArrayAdapter<String> arrayAdapterTT = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, tourism);
        MaterialBetterSpinner materialDesignSpinnerTT = (MaterialBetterSpinner)
                findViewById(R.id.spinner_typeTourism);
        materialDesignSpinnerTT.setAdapter(arrayAdapterTT);


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
