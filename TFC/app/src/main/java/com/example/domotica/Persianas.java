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

public class Persianas extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference refHabitacion = database.getReference("HABITACION");

    DatabaseReference refVentanas, refVentana;
    ToggleButton botonVentana;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persianas);
        // REFERENCIA AL APARTADO DE VENTANAS DE LA HABITACION
        refVentanas = refHabitacion.child("VENTANAS");
        refVentana = refVentanas.child("Ventana-habitacion");
        //REFERENCIA DEL BOTON DE LA VENTANA DE LA HABITACION
        botonVentana = (ToggleButton) findViewById(R.id.toggleButtonVentanaHabitacion);
        // TEXTO QUE SE MUESTRA EN EL BOTON
        botonVentana.setTextOn("CERRAR");
        botonVentana.setTextOff("ABRIR");

        sacarValorBDVentanas(refVentana,botonVentana);
        controlVentana(refVentana,botonVentana);
    }

    private void sacarValorBDVentanas(final DatabaseReference refLed, final ToggleButton toggle_btn ){
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
    private void controlVentana(final DatabaseReference refLed, final ToggleButton toggle_btn ) {
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
