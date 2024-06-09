from .sensorData import SensorData
from .flight import Flight

class Value:
    valueId=0
    value=""
    sensorData=SensorData(0,"","","","")
    flight=Flight(0,0,0,0)
    timestamp=0

    def __init__(self,valueId,value,timestamp):
        self.valueId=valueId
        self.value=value
        self.timestamp=timestamp


    def getValueId(self):
        return self.valueId
    
    def getValue(self):
        return self.value
    
    def getSensorData(self):
        return self.sensorData
    def getFlight(self):
        return self.flight
    
    def getTimestamp(self):
        return self.timestamp
    
    def setValueId(self,valueId):
        self.valueId=valueId
    
    def setValue(self,value):
        self.value=value
    
    def setSensorData(self,sensorData):
        self.sensorData=sensorData

    def setFlight(self,flight):
        self.flight=flight
    
    def setTimestamp(self,timestamp):
        self.timestamp=timestamp
    
