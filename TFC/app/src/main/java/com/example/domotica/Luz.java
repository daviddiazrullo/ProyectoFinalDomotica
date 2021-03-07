package com.example.domotica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Luz extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //REFERENCIAS A LAS ESTANCIAS DE MI CASA
    DatabaseReference refCocina = database.getReference("COCINA");
    DatabaseReference refHabitacion = database.getReference("HABITACION");
    DatabaseReference refSalon = database.getReference("SALON");
    // REFERENECIA A LAS LUCES DE MI CASA
    DatabaseReference refLuces1, refLuces2, refLuces3, refLed1, refled2, refled3, RGB;
    ToggleButton toggleButtonLuzCocina, toggleButtonLuzHabitacion, toggleButtonLuzSalon;
    Button buttonRGB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luz);

        //REFERENCIA A LAS LUCES DE MI CASA
        refLuces2 = refCocina.child("LUCES");
        refLuces3 = refHabitacion.child("LUCES");
        refLuces1 = refSalon.child("LUCES");
        refLed1  = refLuces1.child("led1");
        refled2= refLuces2.child("led2");
        refled3= refLuces3.child("led3");


        // REFERENCIA DEL RGB EN LA BASE DE DATOS
        RGB = refLuces1.child("RGB");


        // REFERENCIA AL BOTON DE LA COCINA
        toggleButtonLuzCocina = (ToggleButton)  findViewById(R.id.toggleButtonLuzCocina);
        // TEXTO QUE SE MUESTRA EN EL BOTON
        toggleButtonLuzCocina.setTextOn("APAGAR");
        toggleButtonLuzCocina.setTextOff("ENCENDER");

        // REFERENCIA AL BOTON DE LA HABITACION
        toggleButtonLuzHabitacion  = (ToggleButton)  findViewById(R.id.toggleButtonLuzHabitacion);
        // TEXTO QUE SE MUESTRA EN EL BOTON
        toggleButtonLuzHabitacion.setTextOn("APAGAR");
        toggleButtonLuzHabitacion.setTextOff("ENCENDER");

        // REFERENCIA DEL BOTON DEL SALON
        toggleButtonLuzSalon   = (ToggleButton)  findViewById(R.id.toggleButtonLuzSalon);
        // TEXTO QUE SE MUESTRA EN EL BOTON
        toggleButtonLuzSalon.setTextOn("APAGAR");
        toggleButtonLuzSalon.setTextOff("ENCENDER");

        //REFERENCIA DEL BOTON RGB
        buttonRGB = (Button) findViewById(R.id.buttonRGB);


        sacarValorBDLuces(refLed1,toggleButtonLuzSalon );
        controlLED(refLed1, toggleButtonLuzSalon);

        sacarValorBDLuces(refled2,toggleButtonLuzCocina);
        controlLED(refled2, toggleButtonLuzCocina);

        sacarValorBDLuces(refled3,toggleButtonLuzHabitacion);
        controlLED(refled3, toggleButtonLuzHabitacion);

        sacarValorRgb(RGB);
        controlRGB(RGB,"0");

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

    private void sacarValorRgb( final DatabaseReference RGB){

        RGB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String dato = dataSnapshot.getValue().toString();
                String prueba = dato.substring(dato.length()-1);
                //textEstadoPulsador.setText(prueba);
                controlRGB(RGB,prueba);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void controlRGB(final DatabaseReference RGB, final String prueba){


        buttonRGB.setOnClickListener(new View.OnClickListener() {
            int contador = 0 ;
            String valor;
            @Override
            public void onClick(View v) {
                String valorAnterior;
                contador++;
                if (prueba.equalsIgnoreCase("0")){

                    buttonRGB.setEnabled(true);
                    buttonRGB.setTextColor(Color.parseColor("#FC1B00"));
                    valor= "LR00";
                    RGB.setValue(valor);

                    buttonRGB.setEnabled(true);
                    buttonRGB.setText("ROJO");
                    buttonRGB.setBackgroundColor(Color.parseColor("#FC1B00"));
                    valor= "LR0R";
                    RGB.setValue(valor);

                }
                if (prueba.equalsIgnoreCase("R") ){

                    buttonRGB.setEnabled(true);
                    buttonRGB.setText("ROJO");
                    buttonRGB.setBackgroundColor(Color.parseColor("#FC1B00"));
                    valor= "LR0R";
                    RGB.setValue(valor);


                    buttonRGB.setEnabled(true);
                    buttonRGB.setText("VERDE");
                    buttonRGB.setBackgroundColor(Color.parseColor("#25FC00"));
                    valor= "LR0G";
                    RGB.setValue(valor);
                }
                if (prueba.equalsIgnoreCase("G")){
                    buttonRGB.setEnabled(true);
                    buttonRGB.setText("AZUL");
                    buttonRGB.setBackgroundColor(Color.parseColor("#0017FC"));
                    valor= "LR0B";
                    RGB.setValue(valor);
                }
                if (prueba.equalsIgnoreCase("B")){
                    buttonRGB.setEnabled(true);
                    buttonRGB.setText("AMARILLO");
                    buttonRGB.setBackgroundColor(Color.parseColor("#FCFC00"));
                    valor = "LR0Y";
                    RGB.setValue(valor);
                }
                if (prueba.equalsIgnoreCase("Y")){
                    buttonRGB.setEnabled(true);
                    buttonRGB.setText("MORADO");
                    buttonRGB.setBackgroundColor(Color.parseColor("#F300FC"));
                    valor = "LR0P";
                    RGB.setValue(valor);
                }
                if (prueba.equalsIgnoreCase("P")){
                    buttonRGB.setEnabled(true);
                    buttonRGB.setText("CELESTE");
                    buttonRGB.setBackgroundColor(Color.parseColor("#00FCEE"));
                    valor = "LR0C";
                    RGB.setValue(valor);
                }
                if (prueba.equalsIgnoreCase("C")){
                    buttonRGB.setEnabled(true);
                    buttonRGB.setTextColor(Color.parseColor("#FC1B00"));
                    valor= "LR00";
                    RGB.setValue(valor);
                }

                if (contador == 7) {
                    contador = 0;
                }
            }

        });
        switch(prueba) {
            case "0":
                buttonRGB.setEnabled(true);
                buttonRGB.setText("ENCENDER");
                buttonRGB.setTextColor(Color.parseColor("#000000"));
                buttonRGB.setBackgroundColor(Color.parseColor("#C7C8C8"));
                RGB.setValue("LR00");
                break;
            case "R":
                buttonRGB.setEnabled(true);
                buttonRGB.setText("ROJO");
                buttonRGB.setTextColor(Color.parseColor("#000000"));
                buttonRGB.setBackgroundColor(Color.parseColor("#FC1B00"));
                RGB.setValue("LR0R");
                break;
            case "G":
                buttonRGB.setEnabled(true);
                buttonRGB.setText("VERDE");
                buttonRGB.setTextColor(Color.parseColor("#000000"));
                buttonRGB.setBackgroundColor(Color.parseColor("#25FC00"));
                RGB.setValue("LR0G");
                break;
            case "B":
                buttonRGB.setEnabled(true);
                buttonRGB.setText("AZUL");
                buttonRGB.setTextColor(Color.parseColor("#000000"));
                buttonRGB.setBackgroundColor(Color.parseColor("#0017FC"));
                RGB.setValue("LR0B");
                break;
            case "Y":
                buttonRGB.setEnabled(true);
                buttonRGB.setText("AMARILLO");
                buttonRGB.setTextColor(Color.parseColor("#000000"));
                buttonRGB.setBackgroundColor(Color.parseColor("#FCFC00"));
                RGB.setValue("LR0Y");
                break;
            case "P":
                buttonRGB.setEnabled(true);
                buttonRGB.setText("MORADO");
                buttonRGB.setTextColor(Color.parseColor("#000000"));
                buttonRGB.setBackgroundColor(Color.parseColor("#F300FC"));
                RGB.setValue("LR0P");
                break;
            case "C":
                buttonRGB.setText("CELESTE");
                buttonRGB.setTextColor(Color.parseColor("#000000"));
                buttonRGB.setBackgroundColor(Color.parseColor("#00FCEE"));
                RGB.setValue("LR0C");
                break;

            default:
                buttonRGB.setEnabled(true);
                buttonRGB.setText("ENCENDER");
                buttonRGB.setTextColor(Color.parseColor("#000000"));
                buttonRGB.setBackgroundColor(Color.parseColor("#C7C8C8"));
                RGB.setValue("LR00");

        }
    }
}