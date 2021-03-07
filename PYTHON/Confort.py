from firebase_admin import db


REF_SALON = 'SALON'
REF_CLIMATIZACION = 'CLIMATIZACION'
REF_TEMPERATURA = 'temperatura'
REF_HUMEDAD = 'humedad'

class confort:
    def controlHumedadTemperatura(self,comando):
        self.refSalon = db.reference(REF_SALON)
        self.refClimatizacion = self.refSalon.child(REF_CLIMATIZACION)
        self.refTemperatura = self.refClimatizacion.child(REF_TEMPERATURA)
        self.refHumedad = self.refClimatizacion.child(REF_HUMEDAD)
        if comando.count("Temperatura") > 0:
            self.refTemperatura.set(comando)
        if comando.count("Humedad") > 0:
            self.refHumedad.set(comando)
