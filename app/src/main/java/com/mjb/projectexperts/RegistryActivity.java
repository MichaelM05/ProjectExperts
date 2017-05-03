package com.mjb.projectexperts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RegistryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);


        /*Spinner spinnerP = (Spinner) findViewById(R.id.spinner_province);
        Spinner spinnerC = (Spinner) findViewById(R.id.spinner_canton);
        Spinner spinnerD = (Spinner) findViewById(R.id.spinner_district);
        String[] provinces = {"San José","Cartago","Heredia","Alajuela","Puntarenas","Limón", "Guanacaste"};
        String[] canton = {"Cantón1","cantón2"};
        String[] district = {"Distrito1","Distrito2"};
        spinnerP.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinces));
        spinnerC.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, canton));
        spinnerD.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, district));*/


        Button btnRegistry = (Button)findViewById(R.id.btn_accept_registry_);
        btnRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(RegistryActivity.this, SearchDestinationActivity.class);
                startActivity(main);
            }
        });


    }
}
