package com.mjb.projectexperts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button btnAccept = (Button)findViewById(R.id.btn_accept_login);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(LoginActivity.this, NewSiteActivity.class);
                startActivity(main);
            }
        });


        Button btnRegistry = (Button)findViewById(R.id.btn_Registry);
        btnRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(LoginActivity.this, RegistryActivity.class);
                startActivity(main);
            }
        });
    }
}
