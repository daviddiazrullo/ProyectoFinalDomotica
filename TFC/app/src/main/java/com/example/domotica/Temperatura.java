package com.example.domotica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Temperatura extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refSalon = database.getReference("SALON");
    DatabaseReference refClimatizacion,refTemperatura,refHumedad;
    TextView textEstadoPulsador , textHumedad, textTemperatura;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);

        refClimatizacion = refSalon.child("CLIMATIZACION");

        //REFERENCIA A LA TEMPERATURA Y LA HUMEDAD DEL SALON
        refTemperatura = refClimatizacion.child("temperatura");
        refHumedad = refClimatizacion.child("humedad");

        textTemperatura = (TextView) findViewById(R.id.textTemperatura);
        textHumedad = (TextView) findViewById(R.id.textHumedad);

        sacarValorClimatizacion( refTemperatura,refHumedad);

    }
    private void sacarValorClimatizacion(final DatabaseReference refTemperatura,final DatabaseReference refHumedad){
        // Para controlar datos de una luz en concreto
        refTemperatura.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Saco el valor que tengo guardo en la ref deLuz1
                String Dato2 = dataSnapshot.getValue().toString();

                // Cambio el valor del text View con el valor de la base de datos
                textTemperatura.setText(Dato2+" Cº");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        refHumedad.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String dato = dataSnapshot.getValue().toString();

                textHumedad.setText(dato+" %");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
