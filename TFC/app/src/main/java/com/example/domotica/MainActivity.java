package com.example.domotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void cambioActivityLuz(View vista) {

        Intent siguiente = new Intent(this, Luz.class);

        startActivity(siguiente);
    }
    public void cambioActivityPersianas(View vista) {

        Intent siguiente = new Intent(this, Persianas.class);

        startActivity(siguiente);
    }
    public void cambioActivityPuertas(View vista) {

        Intent siguiente = new Intent(this, Puertas.class);

        startActivity(siguiente);
    }
    public void cambioActivityTemperatura(View vista) {

        Intent siguiente = new Intent(this, Temperatura.class);

        startActivity(siguiente);
    }
}
