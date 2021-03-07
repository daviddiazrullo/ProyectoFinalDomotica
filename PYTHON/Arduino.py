import serial
import Firebase
import Confort
import Puertas
import Ventanas

arduino = serial.Serial('/dev/ttyUSB0', 115200)
arduinoNano = serial.Serial('/dev/ttyUSB1', 9600)


def leerArduino():
    print("Arduino escuchando")
    comando = ' '
    while True:
        if (arduino.in_waiting > 0):
            line = arduino.readline()
            #IMPRIME EL MENSAJE QUE ENVIA ARDUINO
            #print(line.strip())
            comando = line.strip()
            if comando == 'L01T':
                Firebase.IOT().ControlArduino(comando)
            elif comando == 'L01F':
                Firebase.IOT().ControlArduino(comando)
            if comando == 'L02T':
                Firebase.IOT().ControlArduino(comando)
                comando = ""
            elif comando == 'L02F':
                Firebase.IOT().ControlArduino(comando)
            if comando == 'L03T':
                Firebase.IOT().ControlArduino(comando)
                comando = ""
            elif comando == 'L03F':
                Firebase.IOT().ControlArduino(comando)
            if comando == 'LR00':
                Firebase.IOT().ControlArduino(comando)
                comando = ""
            if comando == 'LR0R':
                Firebase.IOT().ControlArduino(comando)
                comando = ""
            if comando == 'LR0G':
                Firebase.IOT().ControlArduino(comando)
                comando = ""
            if comando == 'LR0B':
                Firebase.IOT().ControlArduino(comando)
                comando = ""
            if comando == 'LR0Y':
                Firebase.IOT().ControlArduino(comando)
                comando = ""
            if comando == 'LR0P':
                Firebase.IOT().ControlArduino(comando)
                comando = ""
            if comando == 'LR0C':
                Firebase.IOT().ControlArduino(comando)
                comando = ""
            if comando == 'P001T':
                Puertas.puertas().Control_puerta(comando)
                comando = ""
            if comando == 'P001F':
                Puertas.puertas().Control_puerta(comando)
                comando = ""
            if comando == 'P002T':
                Puertas.puertas().Control_puerta(comando)
                comando = ""
            if comando == 'P002F':
                Puertas.puertas().Control_puerta(comando)
                comando = ""
            if comando == 'P003T':
                Puertas.puertas().Control_puerta(comando)
                comando = ""
            if comando == 'P003F':
                Puertas.puertas().Control_puerta(comando)
                comando = ""
            if comando == 'V001T':
                Ventanas.ventanas().Control_ventana(comando)
                comando = ""
            if comando == 'V001F':
                Ventanas.ventanas().Control_ventana(comando)
                comando = ""
            else:
                Confort.confort().controlHumedadTemperatura(comando)

def leerArduinoNano():
    print("Arduino escuchando")
    comando = ' '
    while True:
       if (arduinoNano.in_waiting > 0):
           line = arduinoNano.readline()
           #IMPRIME EL MENSAJE QUE ENVIA ARDUINO
           print(line.strip())
           comando = line.strip()
           if comando == 'P004T':
                Puertas.puertas().Control_puerta(comando)
                comando = ""
           if comando == 'P004F':
                Puertas.puertas().Control_puerta(comando)
                comando = ""
