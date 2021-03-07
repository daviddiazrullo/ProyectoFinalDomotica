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
REF_PUERTAS = 'PUERTAS'
REF_LUZ1 = 'led1'
REF_LUZ2 = 'led2'
REF_LUZ3 = 'led3'
REF_RGB = 'RGB'
REF_PUERTA_PRINCIPAL = 'Puerta-principal'



class IOT():
    def iniciar(self):
        cred = credentials.Certificate(PAHT_CRED)
        firebase_admin.initialize_app(cred, {
            'databaseURL': URL_DB
        })

    def estadoActual(self,ref):
        # REFERENCIA A LAS ESTANCIAS DE MI  CASA
        self.refSalon = db.reference(REF_SALON)
        self.refCocina = db.reference(REF_COCINA)
        self.refHabitacion = db.reference(REF_HABITACION)

        # self.estructuraInicialDB() # solo ejecutar la primera vez

        # REFERENCIA AL APARTADO DE LUCES DE LA BASE DE DATOS
        self.refLuces = self.refSalon.child(REF_LUCES)
        self.refLuces2 = self.refCocina.child(REF_LUCES2)
        self.refLuces3 = self.refHabitacion.child(REF_LUCES)
        # REFERENCIA AL APARTADO DE LAS PUERTAS DE LA BASE DE DATOS
        #self.refPuertas = self.refSalon.child(REF_PUERTAS)
        # REFERENCIA A LOS LEDS DE LA CASA
        self.refled1 = self.refLuces.child(REF_LUZ1)  # SALON
        self.refled2 = self.refLuces2.child(REF_LUZ2)  # COCINA
        self.refled3 = self.refLuces3.child(REF_LUZ3)  # HABITACION
        # REFERENCIA AL APARTADO DE LA LUZ RGB DE LA BASE DE DATOS
        self.refRGB = self.refLuces.child(REF_RGB)
        # REFERENCIA A LAS PUERTAS DE LA CASA
        #self.refPuertaPrincipal = self.refPuertas(REF_PUERTA_PRINCIPAL)
        if ref == "refled1":
              estado = self.refled1.get()
        if ref == "refled2":
              estado = self.refled2.get()
        if ref == "refled3":
              estado = self.refled3.get()
        if ref == "refRGB":
              estado = self.refRGB.get()
        #if ref == "Puerta-principal":
        #      estado = self.refPuertaPrincipal.get()
        return estado


    def ControlArduino(self,comando):
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
        self.refled1 = self.refLuces.child(REF_LUZ1)  # SALON
        self.refled2 = self.refLuces2.child(REF_LUZ2)  # COCINA
        self.refled3 = self.refLuces3.child(REF_LUZ3)  # HABITACION
        # REFERENCIA AL APARTADO DE LA LUZ RGB DE LA BASE DE DATOS
        self.refRGB = self.refLuces.child(REF_RGB)
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
        if comando == 'P001T':
            self.refRGB.set("true")
            comando = ""
        if comando == 'P001F':
            self.refRGB.set("false")
            comando = ""
