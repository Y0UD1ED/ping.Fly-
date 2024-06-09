from flask import Flask, request, jsonify
from flask_cors import CORS
import sensors
from entities.flight import Flight
from entities.sensor import Sensor
from entities.sensorData import SensorData
from entities.value import Value
from repository import flight
from repository import values
from repository import sensor
from repository import sensordata



app = Flask(__name__)
CORS(app)



@app.route('/hello', methods=['GET'])
def hello_get():
    name = request.args.get('name', 'world')
    return f"Hello, {name}!"

@app.route('/hello', methods=['POST'])
def hello_post():
    data = request.get_json()
    return jsonify(message=f"Hello, {data['name']}!")

@app.route('/sensors',methods=['GET'])
def sensors_get():
    sensors=sensor.getAllSensors()
    res=[]
    for i in sensors:
        dict={"sensorId":i.getSensorId(),
              "name":i.getName()}
        res.append(dict)
    return jsonify(res)

@app.route('/sensor_datas',methods=['GET'])
def sensorDatas_get():
    sensorIds=request.json['sensorIds']
    sensorData_arr=sensordata.getSensorDatasBySensorIds(sensorIds)
    res=[]
    for i in sensorData_arr:
        dict={"sensorDataId":i.getDataId(),
            "sensorDataName":i.getDataName(),
            "UoM":i.getUoM(),
            "sensor":i.getSensor().__dict__
              }
        res.append(dict)
    return jsonify(res)

@app.route('/valuesLog',methods=['GET'])
def valuesLog_get():
    flightId=request.json
    sensorDataIds=sensors.getSensorIds(flightId)
    sensorData_arr=sensors.getDataSensorsOfFlight(sensorDataIds)
    values_arr=sensors.getValues(sensorData_arr)
    values_req=[]
    for i in values_arr:
        dto={
            "timestamp":i.getTimestamp(),
            "dataId": i.getSensorData().getDataId(),
            "dataName": i.getSensorData().getDataName(),
            "sensorName":i.getSensorData().getSensor().getName(),
            "sensorId":i.getSensorData().getSensor().getSensorId(),
            "UoM":i.getSensorData().getUoM(),
            "dataType":i.getSensorData().getDataType(),
            "value":i.getValue()
        }
        values_req.append(dto)
    return values_req

@app.route('/values',methods=['GET'])
def values_get():
    flightId=request.args.get('flight') 
    sensorDataIds=sensors.getSensorIds(flightId)
    sensorData_arr=sensors.getDataSensorsOfFlight(sensorDataIds)
    values_arr=sensors.getValuesLog(sensorData_arr)
    values_req=[]
    for i in values_arr:
        dto={
            "timestamp":i.getTimestamp(),
            "dataId": i.getSensorData().getDataId(),
            "dataName": i.getSensorData().getDataName(),
            "sensorName":i.getSensorData().getSensor().getName(),
            "sensorId":i.getSensorData().getSensor().getSensorId(),
            "UoM":i.getSensorData().getUoM(),
            "dataType":i.getSensorData().getDataType(),
            "value":i.getValue()
        }
        values_req.append(dto)
    return jsonify(values_req)

@app.route('/values',methods=['POST'])
def values_post():
    flightId=request.args.get('flight') 
    sensorDataIds=sensors.getSensorIds(flightId)
    sensorData_arr=sensors.getDataSensorsOfFlight(sensorDataIds)
    values_arr=sensors.getValuesLog(sensorData_arr)
    values_req=[]
    for i in values_arr:
        dto={
            "timestamp":i.getTimestamp(),
            "dataId": i.getSensorData().getDataId(),
            "dataName": i.getSensorData().getDataName(),
            "sensorName":i.getSensorData().getSensor().getName(),
            "sensorId":i.getSensorData().getSensor().getSensorId(),
            "UoM":i.getSensorData().getUoM(),
            "dataType":i.getSensorData().getDataType(),
            "value":i.getValue()
        }
        values_req.append(dto)
    values.addValues(values_arr,flightId)
    print(values_req)
    return jsonify(values_req)


# @app.route('/values',methods=['POST'])
# def values_post():
#     flightId=request.json
#     sensorDataIds=sensors.getSensorIds(flightId)
#     sensorData_arr=sensors.getDataSensorsOfFlight(sensorDataIds)
#     values_arr=sensors.getValues(sensorData_arr)
#     values.addValues(values_arr,flightId['flightId'])
#     return jsonify("OK!")

@app.route('/value',methods=['POST'])
def value_post():
    body=request.json
    value=(body['flightId'],body['dataId'],body['timestamp'],body['value'],)
    values.addValue(value)
    return jsonify("OK!")

@app.route('/flight',methods=['GET'])
def flight_get():
    flightId=request.args.get('id')
    routes=flight.getRouteForFlight(flightId)
    return jsonify(routes) 

@app.route('/flight',methods=['POST'])
def flight_post():
    fData=request.json
    flightId=flight.addFlight(fData['flightData'])
    dict={'flightId':flightId}
    print(dict)
    return jsonify(dict)

@app.route('/flight_simple',methods=['POST'])
def flightSimple_post():
    fData=request.json
    flightId=flight.addSensorDataForSimpleFlight(fData['sensorIds'])
    dict={'flightId':flightId}
    return jsonify(dict)





if __name__ == '__main__':
    app.run(port=8000)


