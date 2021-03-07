import Firebase
from time import sleep
import serial

arduino = serial.Serial('/dev/ttyUSB0', 115200)


class Luces():

    def controlluces(self):
        print("Metodo luces")
        E1, i = [], 0
        E2, x = [], 0
        E3, z = [], 0
        E4, i = [], 0
        luces = ["refled1","refled2","refled3"]
        estado_anterior1 = Firebase.IOT().estadoActual(luces[0])
        self.ledControlGPIO(estado_anterior1, luces[0])
        estado_anterior2 = Firebase.IOT().estadoActual(luces[1])
        self.ledControlGPIO(estado_anterior2,  luces[1])
        estado_anterior3 = Firebase.IOT().estadoActual(luces[2])
        self.ledControlGPIO(estado_anterior3,  luces[2])
        estado_anterior4 = Firebase.IOT().estadoActual("refRGB")
        self.ledControlGPIO(estado_anterior4, "refRGB")

        E1.append(estado_anterior1)
        E2.append(estado_anterior2)
        E3.append(estado_anterior3)
        E4.append(estado_anterior4)

        while True:
            estado_actual1 = Firebase.IOT().estadoActual(luces[0])
            estado_actual2 = Firebase.IOT().estadoActual(luces[1])
            estado_actual3 = Firebase.IOT().estadoActual(luces[2])

            E1.append(estado_actual1)
            if E1[i] != E1[-1]:
                if estado_actual1 :
                    print('LED 1 ON')
                    arduino.write("L01T")
                else:
                    print('LED 1 OFF')
                    arduino.write('L01F')
            del E1[0]
            i = i + i
            sleep(0.4)

            E2.append(estado_actual2)
            if E2[x] != E2[-1]:
                if estado_actual2 :
                    print('LED 2 ON')
                    arduino.write('L02T')
                else:
                    print('LED 2 OFF')
                    arduino.write('L02F')
            del E2[0]
            x = x + x
            sleep(0.4)

            E3.append(estado_actual3)
            if E3[z] != E3[-1]:
               ref = luces[2]
               self.ledControlGPIO(estado_actual3, ref)
            del E3[0]
            z = z + z
            sleep(0.4)

            estado_actual4 = Firebase.IOT().estadoActual("refRGB")
            E4.append(estado_actual4)
            if E4[i] != E4[-1]:
                ref = "RGB"
                self.ledControlGPIO(estado_actual4, ref)
            del E4[0]
            i = i + i
            sleep(0.4)

    def ledControlGPIO(self, estado, refe):
        if refe == "refled1":
            if estado:
                print('LED 1 ON')
                arduino.write("L01T")
            else:
                print('LED 1 OFF')
                arduino.write('L01F')
        if refe == "refled2":
            if estado:
                print('LED 2 ON')
                arduino.write('L02T')
            else:
                print('LED 2 OFF')
                arduino.write('L02F')
        if refe == "refled3":
            if estado:
                print('LED 3 ON')
                arduino.write('L03T')
            else:
                print('LED 3 OFF')
                arduino.write('L03F')
        if refe == "RGB":
            if estado == 'LR00':
                print('LED RGB OFF')
                arduino.write('LR00')
            if estado == 'LR0R':
                print('LED RGB ROJO')
                arduino.write('LR0R')
            if estado == 'LR0G':
                print('LED RGB VERDE')
                arduino.write('LR0G')
            if estado == 'LR0B':
                print('LED RGB AZUL')
                arduino.write('LR0B')
            if estado == 'LR0Y':
                print('LED RGB AMARILLO')
                arduino.write('LR0Y')
            if estado == 'LR0P':
                print('LED RGB MORADO')
                arduino.write('LR0P')
            if estado == 'LR0C':
                print('LED RGB CELESTE')
                arduino.write('LR0C')
