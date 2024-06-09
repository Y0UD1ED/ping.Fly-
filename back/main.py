from flask import Flask, request, jsonify
import sensors
import copy
import json
from entities.flight import Flight
from entities.sensor import Sensor
from entities.sensorData import SensorData
from entities.value import Value
from repository import flight

app = Flask(__name__)

@app.route('/hello', methods=['GET'])
def hello_get():
    name = request.args.get('name', 'world')
    return f"Hello, {name}!"

@app.route('/hello', methods=['POST'])
def hello_post():
    data = request.get_json()
    return jsonify(message=f"Hello, {data['name']}!")

@app.route('/values',methods=['GET'])
def values_get():
    flightId=request.json
    sensorDataIds=sensors.getSensorIds(flightId)
    sensorData_arr=sensors.getDataSensorsOfFlight(sensorDataIds)
    values_arr=sensors.getValues(sensorData_arr)
    #print(sensorData_arr[0].getLogName())
    values_req=[]
    for i in values_arr:
        dict=i.__dict__
        dict['sensorData']=i.getSensorData().__dict__
        dict['sensorData']['sensor']="None"
        print(dict)
        #j=m.getSensorData().getSensor().to_dict()
        values_req.append(dict)
    return values_req

@app.route('/flight',methods=['POST'])
def flight_post():
    fData=request.json
    print(fData)
    flight.addFlight(fData['oX'],fData['oY'],fData['height'],fData['sensorDataIds'])
    return jsonify("OK!")




if __name__ == '__main__':
    app.run(port=8000)


