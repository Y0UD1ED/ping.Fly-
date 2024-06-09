class Flight:
    flightId=0
    oX=0
    oY=0
    height=0

    def __init__(self,flightId,oX,oY,height):
        self.flightId=flightId
        self.oX=oX
        self.oY=oY
        self.height=height

    def getFlightId(self):
        return self.flightId
    
    def getOX(self):
        return self.oX
    
    def getOY(self):
        return self.oY
    
    def getHeight(self):
        return self.height
    
    def setFlightId(self,flightId):
        self.flightId=flightId
    
    def setOX(self,oX):
        self.oX=oX
    
    def setOY(self,oY):
        self.oY=oY
    
    def setHeight(self,height):
        self.height=height