import sys
from time import sleep
import serial
import signal

import threading
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

PAHT_CRED = '/home/pi/iot/basedatosdomotica-firebase-adminsdk-j7hjo-1add7ac3dc.json'
URL_DB = 'https://basedatosdomotica.firebaseio.com/'
REF_SALON = 'SALON'
REF_COCINA = 'COCINA'
REF_HABITACION = 'HABITACION'
REF_LUCES = 'LUCES'
REF_LUCES2 = 'LUCES'
REF_LUZ1 = 'led1'
REF_LUZ2 = 'led2'
REF_LUZ3 = 'led3'
REF_RGB = 'RGB'
arduino = serial.Serial('/dev/ttyUSB0', 115200)


class IOT():

    def __init__(self):
        cred = credentials.Certificate(PAHT_CRED)
        firebase_admin.initialize_app(cred, {
            'databaseURL': URL_DB
        })

        # REFERENCIA A LAS ESTANCIAS DE MI  CASA
        self.refSalon = db.reference(REF_SALON)
        self.refCocina = db.reference(REF_COCINA)
        self.refHabitacion = db.reference(REF_HABITACION)

        # self.estructuraInicialDB() # solo ejecutar la primera vez

        # REFERENCIA AL APARTADO DE LUCES DE LA BASE DE DATOS
        self.refLuces = self.refSalon.child(REF_LUCES)
        self.refLuces2 = self.refCocina.child(REF_LUCES2)
        self.refLuces3 = self.refHabitacion.child(REF_LUCES)
        # REFERENCIA A LOS LEDS DE LA CASA
        self.refled1 = self.refLuces.child(REF_LUZ1) #SALON
        self.refled2 = self.refLuces2.child(REF_LUZ2)#COCINA
        self.refled3 = self.refLuces3.child(REF_LUZ3)#HABITACION
        # REFERENCIA AL APARTADO DE LA LUZ RGB DE LA BASE DE DATOS
        self.refRGB = self.refLuces.child(REF_RGB)

    def estructuraInicialDB(self):
        self.refHome.set({
            "COCINA" : {
                "LUCES" : {
                  "led2" : true
                }
              },
              "HABITACION" : {
                "LUCES" : {
                  "led3" : true
                }
              },
              "SALON" : {
                "LUCES" : {
                  "RGB" : "LR0G",
                  "led1" : true
                }
              }
        })

    def ledControlGPIO(self, estado, refe):

        if refe == "refled1":
            if estado:
                print('LED ON')
                arduino.write("L01T")
            else:
                print('LED OFF')
                arduino.write('L01F')
        if refe == "refled2":
            if estado:
                print('LED ON')
                arduino.write('L02T')
            else:
                print('LED OFF')
                arduino.write('L02F')
        if refe == "refled3":
            if estado:
                print('LED ON')
                arduino.write('L03T')
            else:
                print('LED OFF')
                arduino.write('L03F')
        if refe == "RGB":
            if estado == 'LR00':
                print('LED OFF')
                arduino.write('LR00')
            if estado == 'LR0R':
                print('LED ROJO')
                arduino.write('LR0R')
            if estado == 'LR0G':
                print('LED VERDE')
                arduino.write('LR0G')
            if estado == 'LR0B':
                print('LED AZUL')
                arduino.write('LR0B')
            if estado == 'LR0Y':
                print('LED AMARILLO')
                arduino.write('LR0Y')
            if estado == 'LR0P':
                print('LED MORADO')
                arduino.write('LR0P')
            if estado == 'LR0C':
                print('LED CELESTE')
                arduino.write('LR0C')

    def luces(self):
        print("Metodo luces")
        E1, i = [], 0
        E2, x = [], 0
        E3, z = [], 0
        luces = ["refled1","refled2","refled3"]
        estado_anterior1 = self.refled1.get()
        self.ledControlGPIO(estado_anterior1, luces[0])
        estado_anterior2 = self.refled2.get()
        self.ledControlGPIO(estado_anterior2,  luces[1])
        estado_anterior3 = self.refled3.get()
        self.ledControlGPIO(estado_anterior3,  luces[2])
        E1.append(estado_anterior1)
        E2.append(estado_anterior2)
        E3.append(estado_anterior3)
        while True:
            estado_actual1 = self.refled1.get()
            estado_actual2 = self.refled2.get()
            estado_actual3 = self.refled3.get()
            E1.append(estado_actual1)
            E2.append(estado_actual2)
            E3.append(estado_actual3)
            if E1[i] != E1[-1]:
                ref = luces[0]
                self.ledControlGPIO(estado_actual1, ref)
            del E1[0]
            i = i + i
            sleep(0.4)
            if E2[x] != E2[-1]:
                ref = luces[1]
                self.ledControlGPIO(estado_actual2, ref)
            del E2[0]
            x = x + x
            sleep(0.4)
            if E3[z] != E3[-1]:
               ref = luces[2]
               self.ledControlGPIO(estado_actual3, ref)
            del E3[0]
            z = z + z
            sleep(0.4)

    def rgb(self):
        E, i = [], 0
        estado_anterior = self.refRGB.get()
        self.ledControlGPIO(estado_anterior, "RGB")
        E.append(estado_anterior)
        while True:
            estado_actual = self.refRGB.get()
            E.append(estado_actual)
            if E[i] != E[-1]:
                ref = "RGB"
                self.ledControlGPIO(estado_actual, ref)
            del E[0]
            i = i + i
            sleep(0.4)

    def leerArduino(self):
        print("Arduino escuchando")
        comando = ' '
        while True:
            if (arduino.in_waiting > 0):
                line = arduino.readline()
                print(line.strip())
                comando = line.strip()
                if comando == 'L01T':
                    self.refled1.set("true")
                    comando = ""
                elif comando == 'L01F':
                    self.refled1.set('false')
                if comando == 'L02T':
                    self.refled2.set("true")
                    comando = ""
                elif comando == 'L02F':
                    self.refled2.set('false')
                if comando == 'L03T':
                    self.refled3.set("true")
                    comando = ""
                elif comando == 'L03F':
                    self.refled3.set('false')
                if comando == 'LR00':
                    self.refRGB.set("LR00")
                    comando = ""
                if comando == 'LR0R':
                    self.refRGB.set("LR0R")
                    comando = ""
                if comando == 'LR0G':
                    self.refRGB.set("LR0G")
                    comando = ""
                if comando == 'LR0B':
                    self.refRGB.set("LR0B")
                    comando = ""
                if comando == 'LR0Y':
                    self.refRGB.set("LR0Y")
                    comando = ""
                if comando == 'LR0P':
                    self.refRGB.set("LR0P")
                    comando = ""
                if comando == 'LR0C':
                    self.refRGB.set("LR0C")
                    comando = ""




print('START !')
iot = IOT()

subproceso_leerArduino = threading.Thread(target=iot.leerArduino)
subproceso_leerArduino.daemon = True

subproceso_luces = threading.Thread(target=iot.luces)
subproceso_luces.daemon = True

subproceso_RGB = threading.Thread(target=iot.rgb)
subproceso_RGB.daemon = True

subproceso_luces.start()

subproceso_RGB.start()

subproceso_leerArduino.start()

signal.pause()
