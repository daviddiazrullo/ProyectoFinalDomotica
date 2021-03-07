//LIBRERIA PARA EL SENSOR DE HUMEDAD Y TEMPERATURA
#include <DHT.h>
#include <DHT_U.h>
#include "DHT.h"
#define DHTTYPE DHT11   // DHT 11

//LIBRERIA PARA LOS SERVOS
#include <Servo.h>
//CONTROL DE LAS LUCES

int LED1 = 30; //LED SALON
int LED2 = 31; //LED COCINA
int LED3 = 32; //LED HABITACION
int pulsador1 = 27; // PULSADOR SALON
int pulsador2 = 28; // PULSADOR COCINA
int pulsador3 = 29; // PULSADOR HABITACION

int estadoBoton1 = 0;
int estadoBoton2 = 0;
int estadoBoton3 = 0;

// CONTROL LUZ RGB
int LED_R = 33;
int LED_G = 34;
int LED_B = 35;

int PULSADORRGB = 26;

int contadorRgb = 0;
int estadobotonRgb;
String mensaje;
// CONTROL DE TEMPERATURA Y HUMEDAD
const int DHTPin = 36; // SENSOR DE TEMPERATURA Y HUMEDAD
DHT dht(DHTPin, DHTTYPE);
int presa = 0;
//CONTROL DE SERVOS
// CONTROL SERVO PUERTA PRINCIPAL
Servo puertaPrincipal;  // create servo object to control a servo
int pos = 0;
int pulsadorPuertaPricipal = 40;
int estadoBoton = 0;
int tiempo = 0 ;
boolean estado = false;
// CONTROL SERVO PUERTA HABITACION
Servo puertaHabitacion;  // create servo object to control a servoint pulsadorPuertaHabitacion = 42;
int estadoBotonHabitacion = 0;
boolean estadoHabitacion = false;
int pulsadorPuertaHabitacion = 42;
//CONTROL SERVO PUERTA COCINA
Servo puertaCocina;  // create servo object to control a servoint pulsadorPuertaHabitacion = 42;
int estadoBotonCocina = 0;
boolean estadoCocina = false;
int pulsadorPuertaCocina = 44;

Servo Puertas[3] = {puertaPrincipal , puertaHabitacion, puertaCocina };

// PARTE PARA CONTROLAR LAS VENTANAS
int botonventana = 45;
int pos1 = 90;
Servo VIzquierda;
Servo VDerecha;
Servo ventanaHabitacion[2] = { VIzquierda, VDerecha };


void setup () {
  Serial.begin(115200);

  ////CONTROL DE LAS LUCES
  pinMode (LED1, OUTPUT);       //configurado como salida
  pinMode (LED2, OUTPUT);       //configurado como salida
  pinMode (LED3, OUTPUT);       //configurado como salida

  pinMode (pulsador1, INPUT);   //configurado de entrada
  pinMode (pulsador2, INPUT);   //configurado de entrada
  pinMode (pulsador3, INPUT);   //pulsador configurado como entrada

  // CONTROL LUZ RGB
  pinMode(LED_R, OUTPUT);       //configurado como salida
  pinMode(LED_G, OUTPUT);       //configurado como salida
  pinMode(LED_B, OUTPUT);       //configurado como salida

  pinMode(PULSADORRGB, INPUT);  //configurado de entrada

  // SENSOR DE TEMPERATURA Y HUMEDAD
  dht.begin();

  // CONTROL DE LOS SERVOS
  //PUERTA PRINCIPAL
  pinMode (pulsadorPuertaPricipal, INPUT);   //configurado de entrada
  Puertas[0].attach(37);
  //PUERTA HABITACION
  pinMode (pulsadorPuertaHabitacion, INPUT);   //configurado de entrada
  Puertas[1].attach(41);
  //PUERTA COCINA
  pinMode (pulsadorPuertaCocina, INPUT);   //configurado de entrada
  Puertas[2].attach(43);

  // PARTE PARA CONTROLAR LAS VENTANAS
  pinMode (botonventana, INPUT);
  ventanaHabitacion[0].attach(46);
  ventanaHabitacion[1].attach(47);
}

void loop() {
  // PARTE PARA CONTROLAR EL LED CON LA RASPBERRY

  if (Serial.available()) {
    String estado = Serial.readString();
    if (estado == "L01T") {
      digitalWrite(LED1, HIGH);
    } else if (estado == "L01F") {
      digitalWrite(LED1, LOW);
    }
    if (estado == "L02T") {
      digitalWrite(LED2, HIGH);
    } else if (estado == "L02F") {
      digitalWrite(LED2, LOW);
    }
    if (estado == "L03T") {
      digitalWrite(LED3, HIGH);
    } else if (estado == "L03F") {
      digitalWrite(LED3, LOW);
    }
    if (estado == "LR00") {
      digitalWrite(LED_R, LOW);
      digitalWrite(LED_G, LOW);
      digitalWrite(LED_B, LOW);
      contadorRgb = 6;
    }
    if (estado == "LR0R") {
      digitalWrite(LED_R, HIGH);
      digitalWrite(LED_G, LOW);
      digitalWrite(LED_B, LOW);
      contadorRgb = 0;
    }
    if (estado == "LR0G") {
      digitalWrite(LED_R, LOW);
      digitalWrite(LED_G, HIGH);
      digitalWrite(LED_B, LOW);
      contadorRgb = 1;
    }
    if (estado == "LR0B") {
      digitalWrite(LED_R, LOW);
      digitalWrite(LED_G, LOW);
      digitalWrite(LED_B, HIGH);
      contadorRgb = 2;
    }
    if (estado == "LR0Y") {
      digitalWrite(LED_R, HIGH);
      digitalWrite(LED_G, HIGH);
      digitalWrite(LED_B, LOW);
      contadorRgb = 3;
    }
    if (estado == "LR0P") {
      digitalWrite(LED_R, HIGH);
      digitalWrite(LED_G, LOW);
      digitalWrite(LED_B, HIGH);
      contadorRgb = 4;
    }
    if (estado == "LR0C") {
      digitalWrite(LED_R, LOW);
      digitalWrite(LED_G, HIGH);
      digitalWrite(LED_B, HIGH);
      contadorRgb = 5;
    }
    if (estado == "P001T") {
      String codigo = "";
      int envio = 0;
      PuertaPrincipalAbrirADerecha(envio, Puertas[0], true, codigo);
    } else if (estado == "P001F") {
      String codigo = "";
      int envio = 0;
      PuertaPrincipalCerrarDerecha(envio, Puertas[0], false , codigo);
    }
    if (estado == "P002T") {
      String codigo = "";
      int envio = 0;
      PuertaPrincipalAbrirADerecha(envio, Puertas[1], true, codigo);
    } else if (estado == "P002F") {
      String codigo = "";
      int envio = 0;
      PuertaPrincipalCerrarDerecha(envio, Puertas[1] , false, codigo);
    }
    if (estado == "P003T") {
      String codigo = "";
      int envio = 0;
      PuertaPrincipalAbrirADerecha(envio, Puertas[2], true, codigo);
    } else if (estado == "P003F") {
      String codigo = "";
      int envio = 0;
      PuertaPrincipalCerrarDerecha(envio, Puertas[2] , false, codigo);
    }
    if (estado == "V001T") {
      String codigo = "";
      AbrirVentana();
    } else if (estado == "V001F") {
      String codigo = "";
      CerrarVentana();
    }
  }

  // CONTROL DEL PRIMER LED
  estadoBoton1 = digitalRead(pulsador1);
  if (estadoBoton1 == HIGH) {
    String texto = "L01";
    ControlLuzLed(LED1 , texto);
  }
  // CONTROL DEL SEGUNDO LED
  estadoBoton2 = digitalRead(pulsador2);
  if (estadoBoton2 == HIGH) {
    String texto = "L02";
    ControlLuzLed(LED2 , texto);
  }
  // CONTROL DEL TERCER LED
  estadoBoton3 = digitalRead(pulsador3);
  if (estadoBoton3 == HIGH) {
    String texto = "L03";
    ControlLuzLed(LED3 , texto);
  }
  // CONTROL RGB
  estadobotonRgb = digitalRead(PULSADORRGB);
  if (estadobotonRgb == HIGH) {
    delay(360);
    contadorRgb = contadorRgb + 1 ;
    if (contadorRgb >= 7) {
      contadorRgb = 0;
    }
    ControlLuzRgb(contadorRgb);
  }

  //30000 lo ideal
  if (presa > 999999) {
    Temperatura(presa);
  }
  presa = presa + 1;

  //CONTROL PUERTA PRINCIPAL
  String codigo = "";
  estadoBoton = digitalRead(pulsadorPuertaPricipal);
  int envio = 1;
  if (estado == true) {
    tiempo++;
  }
  if (estadoBoton == HIGH && estado == false) {
    codigo = "P001T";
    PuertaPrincipalAbrirADerecha(envio, Puertas[0], false, codigo);
    estado = true;
  }
  else if (estadoBoton == HIGH && estado == true) {
    codigo = "P001F";
    envio = 1;
    PuertaPrincipalCerrarDerecha(envio, Puertas[0], true, codigo);
    estado = false;

  } else if (tiempo >= 15 ) {
    codigo = "P001F";
    envio = 1;
    //PuertaPrincipalCerrarDerecha(envio, Puertas[0],estado,codigo);
    tiempo = 0;
  }
  delay(360);
  //CONTROL PUERTA HABITACION
  estadoBotonHabitacion = digitalRead(pulsadorPuertaHabitacion);
  int envioHabitacion = 1;

  if (estadoBotonHabitacion == HIGH && estadoHabitacion == false) {
    codigo = "P002T";
    PuertaPrincipalAbrirADerecha(envio, Puertas[1] , false, codigo);
    estadoHabitacion = true;
  }
  else if (estadoBotonHabitacion == HIGH && estadoHabitacion == true) {
    envio = 1;
    codigo = "P002F";
    PuertaPrincipalCerrarDerecha(envio, Puertas[1], true, codigo);
    estadoHabitacion = false;

  }
  delay(360);
  //CONTROL PUERTA COCINA
  estadoBotonCocina = digitalRead(pulsadorPuertaCocina );
  int envioCocina = 1;

  if (estadoBotonCocina == HIGH && estadoCocina == false) {
    codigo = "P003T";
    PuertaPrincipalAbrirADerecha(envio, Puertas[2] , false, codigo);
    estadoCocina = true;
  }
  else if (estadoBotonCocina == HIGH && estadoCocina == true) {
    envio = 1;
    codigo = "P003F";
    PuertaPrincipalCerrarDerecha(envio, Puertas[2], true, codigo);
    estadoCocina = false;

  }
  delay(360);

  // PARTE PARA CONTROLAR LAS VENTANAS
  int estadoBotonVentana = digitalRead(botonventana);
  if (estadoBotonVentana == HIGH && pos1 == 90 || estadoBotonVentana == HIGH && pos1 == 89  ) {
    AbrirVentana();
    Serial.println("V001T");

  }
  delay(36);

  estadoBotonVentana = digitalRead(botonventana);
  if (estadoBotonVentana == HIGH && pos1 == 181) {
    CerrarVentana();
    Serial.println("V001F");
  }
}
//METODO PARA EL CONTROL DE LA PUERTA PRINCIPAL

void PuertaPrincipalAbrirADerecha(int envio, Servo Puerta, boolean estadoPuerta, String codigo) {
  Serial.println(Puerta.read());
  if (Puerta.read() != 90) {
    for (pos = 0; pos <= 90; pos += 1) { // goes from 0 degrees to 180 degrees
      // in steps of 1 degree
      Puerta.write(pos);              // tell servo to go to position in variable 'pos'
      delay(15);
      // waits 15ms for the servo to reach the position
      //estadoPuerta = true;
    }
    if (envio == 1) {
      Serial.println(codigo);
    }
  }
}
void PuertaPrincipalCerrarDerecha(int envio, Servo Puerta , boolean estadoPuerta , String codigo) {
  Serial.println(Puerta.read());
  if (Puerta.read() != 0) {
    for (pos = 90; pos >= 0; pos -= 1) { // goes from 180 degrees to 0 degrees
      Puerta.write(pos);              // tell servo to go to position in variable 'pos'
      delay(15);
      //estadoPuerta = false;
      tiempo = 0;
    }
    if (envio == 1) {
      Serial.println(codigo);
    }
  }
}

//METODO PARA CONTROL EL SENSOR DE TEMPERATURA
void Temperatura(int presa) {

  float h = dht.readHumidity();
  float t = dht.readTemperature();

  if (isnan(h) || isnan(t)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
  Serial.print("Humedad : ");
  Serial.println(h);
  Serial.print("Temperatura : ");
  Serial.println(t);
  presa = 0;
}

//METODO PARA CONTROL LA LUZ RGB

void ControlLuzRgb(int contadorRgb) {
  switch (contadorRgb) {
    case 0:
      //ROJO
      digitalWrite(LED_R, HIGH);
      digitalWrite(LED_G, LOW);
      digitalWrite(LED_B, LOW);
      mensaje = "LR0R";
      Serial.println(mensaje);
      break;
    case 1:
      //VERDE
      digitalWrite(LED_R, LOW);
      digitalWrite(LED_G, HIGH);
      digitalWrite(LED_B, LOW);
      mensaje = "LR0G";
      Serial.println(mensaje);
      break;
    case 2:
      //AZUL
      digitalWrite(LED_R, LOW);
      digitalWrite(LED_G, LOW);
      digitalWrite(LED_B, HIGH);
      mensaje = "LR0B";
      Serial.println(mensaje);
      break;
    case 3:
      //AMARILLO
      digitalWrite(LED_R, HIGH);
      digitalWrite(LED_G, HIGH);
      digitalWrite(LED_B, LOW);
      mensaje = "LR0Y";
      Serial.println(mensaje);
      break;
    case 4:
      //MORADO
      digitalWrite(LED_R, HIGH);
      digitalWrite(LED_G, LOW);
      digitalWrite(LED_B, HIGH);
      mensaje = "LR0P";
      Serial.println(mensaje);
      break;
    case 5:
      //CELESTE
      digitalWrite(LED_R, LOW);
      digitalWrite(LED_G, HIGH);
      digitalWrite(LED_B, HIGH);
      mensaje = "LR0C";
      Serial.println(mensaje);
      break;
    case 6:
      //APAGADO
      digitalWrite(LED_R, LOW);
      digitalWrite(LED_G, LOW);
      digitalWrite(LED_B, LOW);
      mensaje = "LR00";
      Serial.println(mensaje);
      break;
    default:
      digitalWrite(LED_R, LOW);
      digitalWrite(LED_G, LOW);
      digitalWrite(LED_B, LOW);
      mensaje = "LR00";
      Serial.println(mensaje);
      break;
  }
}
//METODO PARA EL CONTROL DE LUCES LEDS
void ControlLuzLed(int LED , String texto ) {
  if (digitalRead(LED) == HIGH) {
    Serial.println(texto + "F");
    digitalWrite(LED, LOW);
    delay(360);
  } else {
    Serial.println(texto + "T" );
    digitalWrite(LED, HIGH);
    delay(360);
  }
}
// Metodo para controlar las ventanas
void AbrirVentana() {
  if (pos1 == 90 || pos1 == 89  ) {
    for (pos1 = 90; pos1 <= 180; pos1 += 1) { // Aumenta la posicion en grados desde 0 a 90
      ventanaHabitacion[0].write(pos1);              // Coloca el primer servo en la posicion actual
      ventanaHabitacion[1].write(180 - pos1);        // Coloca el segundo servo en la posicion opuesta
      delay(25);                       // Detiene el avance 15ms para dar tiempo al servo a moverse
    }
  }
}
void CerrarVentana() {
  if (pos1 == 180 || pos1 == 181  ) {
    for (pos1 = 180; pos1 >= 90; pos1 -= 1) { // Disminuye la posicion en grados de 90 a 0
      ventanaHabitacion[0].write(pos1);              // Coloca el primer servo en la posicion actual     Serial.println("Primero");
      ventanaHabitacion[1].write(180 - pos1);       // Coloca el segundo servo en la posicion opuesta
      delay(25);                       // Detiene el avance 15ms para dar tiempo al servo a moverse
    }
  }
}
