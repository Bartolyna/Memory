package com.cavanosa.tutorialmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    Button login, registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        login = findViewById(R.id.boton_login);
        registro = findViewById(R.id.boton_registro);
        //VISTA LOGIN
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Main.this, Login.class);
                startActivity(intent);
            }
        });
        //VISTA REGISTRO
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Registro.class);
                startActivity(intent);
            }
        });
    }
}