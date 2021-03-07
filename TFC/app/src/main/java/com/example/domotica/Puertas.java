package com.example.domotica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Puertas extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //REFERENCIAS A LAS ESTANCIAS DE MI CASA
    DatabaseReference refCocina = database.getReference("COCINA");
    DatabaseReference refHabitacion = database.getReference("HABITACION");
    DatabaseReference refSalon = database.getReference("SALON");
    DatabaseReference refPatio = database.getReference("PATIO");
    // REFERENECIA A LAS PUERTAS DE MI CASA

    DatabaseReference refPuertasPatio , refPuertaCorredera ,refPuertasSalon,refPuertaEntrada, refPuertaHabitacion,refPuertasHabitacion, refPuertaCocina,refPuertasCocina ;
    ToggleButton toggleButtonPuertaCorredera , buttonPsalon,buttonPhabitacion,buttonPcocina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puertas);
        // REFERENCIA AL APARTADO DE PUERTAS DEL SALON
        refPuertasSalon = refSalon.child("PUERTAS");

        // REFERENCIA AL APARTADO DE PUERTAS DE LA HABITACION
        refPuertasHabitacion = refHabitacion.child("PUERTAS");

        // REFERENCIA AL APARTADO DE PUERTAS DE LA COCINA
        refPuertasCocina = refCocina.child("PUERTAS");

        // REFERENCIA AL APARTADO DE PUERTAS DEL PATIO
        refPuertasPatio = refPatio.child("PUERTAS");

        // REFERENCIA AL APARTADO DE LA PUERTA CORREDERA DEL PATIO
        refPuertaCorredera = refPuertasPatio.child("Puerta-Corredera");

        // REFERENCIA AL APARTADO DE PUERTAS DEL SALON
        refPuertaEntrada = refPuertasSalon.child("Puerta-principal");

        // REFERENCIA AL APARTADO DE PUERTA DE LA HABITACION
        refPuertaHabitacion = refPuertasHabitacion.child("Puerta-habitacion");

        // REFERENCIA AL APARTADO DE PUERTA DE LA HABITACION
        refPuertaCocina = refPuertasCocina.child("Puerta-cocina");


        //REFERENCIA DEL BOTON DE LA PUERTA DEL PATIO
        toggleButtonPuertaCorredera = (ToggleButton) findViewById(R.id.toggleButtonPuertaCorredera);
        // TEXTO QUE SE MUESTRA EN EL BOTON DE LA PUERTA PRINCIPAL
        toggleButtonPuertaCorredera.setTextOn("CERRAR");
        toggleButtonPuertaCorredera.setTextOff("ABRIR");

        //REFERENCIA DEL BOTON DE LA PUERTA DEL SALON
        buttonPsalon = (ToggleButton) findViewById(R.id.toggleButtonPuertaSalon);
        // TEXTO QUE SE MUESTRA EN EL BOTON
        buttonPsalon.setTextOn("CERRAR");
        buttonPsalon.setTextOff("ABRIR");

        //REFERENCIA DEL BOTON DE LA PUERTA DE LA HABITACION
        buttonPhabitacion = (ToggleButton) findViewById(R.id.toggleButtonPuertaHabitacion);
        // TEXTO QUE SE MUESTRA EN EL BOTON
        buttonPhabitacion.setTextOn("CERRAR");
        buttonPhabitacion.setTextOff("ABRIR");

        //REFERENCIA DEL BOTON DE LA PUERTA DE LA COCINA
        buttonPcocina = (ToggleButton) findViewById(R.id.toggleButtonPuertaCocina);
        // TEXTO QUE SE MUESTRA EN EL BOTON
        buttonPcocina.setTextOn("CERRAR");
        buttonPcocina.setTextOff("ABRIR");

        sacarValorBDLuces(refPuertaCorredera,toggleButtonPuertaCorredera);
        controlLED(refPuertaCorredera, toggleButtonPuertaCorredera);

        sacarValorBDLuces(refPuertaEntrada,buttonPsalon);
        controlLED(refPuertaEntrada, buttonPsalon);

        sacarValorBDLuces(refPuertaHabitacion,buttonPhabitacion);
        controlLED(refPuertaHabitacion, buttonPhabitacion);

        sacarValorBDLuces(refPuertaCocina,buttonPcocina);
        controlLED(refPuertaCocina, buttonPcocina);
    }
    private void sacarValorBDLuces(final DatabaseReference refLed, final ToggleButton toggle_btn ){
        // Para controlar datos de una luz en concreto
        refLed.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Saco el valor que tengo guardo en la ref deLuz1
                String Dato2 = dataSnapshot.getValue().toString();
                // Paso el String de la base de datos a boolean para controlar el boton
                boolean bandera= Boolean.parseBoolean(Dato2);
                // Control del cambio del estado del botoon
                toggle_btn.setChecked(bandera);
                // Cambio el valor del text Vieew con el valor de la base de datos
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void controlLED(final DatabaseReference refLed, final ToggleButton toggle_btn ) {
        // Control del boton tipo Toogle
        toggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Cambio el valor de la base de datos
                refLed.setValue(isChecked);

                // linea que cambia el valor de un textview sacando el valos del pulsador
                //textEstadoPulsador.setText(refLed.getDatabase().getReference().toString());
            }
        });

        ;
    }
}
