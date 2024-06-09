class Sensor:
    sensorId=0
    name=""

    def __init__(self, sensorId, name):
        self.sensorId = sensorId
        self.name = name

    def to_dict(self):
        dict=self.__dict__
        return dict

    def getSensorId(self):
        return self.sensorId
    
    def getName(self):
        return self.name
    
    def setSensorId(self,sensorId):
        self.sensorId=sensorId
    
    def setName(self,name):
        self.name=name
