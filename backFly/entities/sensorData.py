from .sensor import Sensor
class SensorData:
    dataId=0
    sensor=Sensor(0,"")
    dataName=""
    dataType=""
    logName=""
    UoM=""

    def __init__(self,dataId,dataName,dataType,logName,UoM):
        self.dataId=dataId
        self.dataName=dataName
        self.dataType=dataType
        self.logName=logName
        self.UoM=UoM
    
    def getDataId(self):
        return self.dataId
    
    def getSensor(self):
        return self.sensor
    
    def getDataName(self):
        return self.dataName
    
    def getDataType(self):
        return self.dataType
    
    def getLogName(self):
        return self.logName
    
    def getUoM(self):
        return self.UoM
    
    def getSensorDict(self):
        d={
            'sensorId':self.sensor.getSensorId(),
            'name':self.sensor.getName()
        }
        return d
    
    def setDataId(self,dataId):
        self.dataId=dataId
    
    def setSensor(self,sensor):
        self.sensor=sensor
    
    def setDataName(self,dataName):
        self.dataName=dataName
    
    def setDataType(self,dataType):
        self.dataType=dataType
    
    def setLogName(self,logName):
        self.logName=logName

    def setUoM(self,UoM):
        self.UoM=UoM