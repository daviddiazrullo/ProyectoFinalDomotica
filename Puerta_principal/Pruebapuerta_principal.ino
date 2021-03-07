int IN3 = 4;      // IN3 a pin digital 7
int IN4 = 3;      // IN4 a pin digital 8
int ENB = A0;      // ENA a pin digital 9
int Boton = 6 ;
int Fcarrera = 5 ;
int Fcarrera2 = 7 ;
int movimiento = 0;

void setup() {
  Serial.begin(9600);

  // put your setup code here, to run once:
  pinMode(IN3, OUTPUT);   // IN3 como salida
  pinMode(IN4, OUTPUT);   // IN4 como salida
  pinMode(ENB, OUTPUT);   // ENB como salida
  pinMode(Boton, INPUT);   // boton como entrada
  pinMode(Fcarrera, INPUT);   // boton como entrada
  pinMode(Fcarrera2, INPUT);   // boton como entrada

}

void loop() {

  int botonFinalDeCarrera2;
  int botonFinalDeCarrera = digitalRead(Fcarrera);
  if (Serial.available()) {
    String estado = Serial.readString();
    if (estado == "P004T") {
      botonFinalDeCarrera2 = digitalRead(Fcarrera2);
         while ( botonFinalDeCarrera2 == LOW ) {
      while ( botonFinalDeCarrera2 == LOW ) {
        Avance();
        botonFinalDeCarrera2 = digitalRead(Fcarrera2);
      }
      Stop();
      delay(5000);
      botonFinalDeCarrera = digitalRead(Fcarrera);
      while (botonFinalDeCarrera == LOW ) {
        Retroceder();
        
        botonFinalDeCarrera = digitalRead(Fcarrera);
      }
      Stop();
      Serial.println("P004F");
    }
    }  if (estado == "P004F") {
      while ( botonFinalDeCarrera == LOW) {
        Retroceder();
      }
      Stop();
    }
  }

  botonFinalDeCarrera = digitalRead(Fcarrera);
  int estadoboton = digitalRead(Boton);
  botonFinalDeCarrera2 = digitalRead(Fcarrera2);

  if (estadoboton == HIGH ) {
          Serial.println("hola");
    botonFinalDeCarrera2 = digitalRead(Fcarrera2);
    while ( botonFinalDeCarrera2 == LOW ) {
      while ( botonFinalDeCarrera2 == LOW ) {
        Avance();
        botonFinalDeCarrera2 = digitalRead(Fcarrera2);
      }
      Stop();
      Serial.println("P004T");
      delay(5000);
      botonFinalDeCarrera = digitalRead(Fcarrera);
      while (botonFinalDeCarrera == LOW ) {
        Retroceder();
        
        botonFinalDeCarrera = digitalRead(Fcarrera);
      }
      Stop();
      Serial.println("P004F");
    }


  }
}

void Stop() {
  analogWrite(ENB, 0);  // velocidad mediante PWM en ENB
  digitalWrite(IN3, LOW); // IN3 a cero logico
  digitalWrite(IN4, LOW);  // IN4 a uno logico
}
void Avance() { // funcion para avance de motor A

  analogWrite(ENB, 130);  // velocidad mediante PWM en ENB
  digitalWrite(IN3, LOW); // IN3 a cero logico
  digitalWrite(IN4, HIGH);  // IN4 a uno logico
}

void Retroceder() { // funcion para avance de motor A

  analogWrite(ENB, 130);  // velocidad mediante PWM en ENB
  digitalWrite(IN3, HIGH); // IN3 a cero logico
  digitalWrite(IN4, LOW);  // IN4 a uno logico
}
