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


        String[] duracion = {"1 HORA","2 HORA","3 HORA"};
        String[] actividad = {"Actividad 1","Actividad 2"};
        String[] distancia = {"1 KM","2 KM"};

        ArrayAdapter<String> arrayAdapterLocation = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, distancia);
        MaterialBetterSpinner materialDesignSpinnerLocation = (MaterialBetterSpinner)
                findViewById(R.id.spinner_actividad);
        materialDesignSpinnerLocation.setAdapter(arrayAdapterLocation);

        ArrayAdapter<String> arrayAdapterActivity = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, distancia);
        MaterialBetterSpinner materialDesignSpinnerActivity = (MaterialBetterSpinner)
                findViewById(R.id.spinner_distancia);
        materialDesignSpinnerActivity.setAdapter(arrayAdapterActivity);

        ArrayAdapter<String> arrayAdapterTT = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, duracion);
        MaterialBetterSpinner materialDesignSpinnerTT = (MaterialBetterSpinner)
                findViewById(R.id.spinner_duracion);
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




    }
}
