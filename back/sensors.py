import psycopg2
from entities.flight import Flight
from entities.sensor import Sensor
from entities.sensorData import SensorData
from entities.value import Value
from Ulog import ULog
import ast

order=0

def getSensorIds(flightId):
    conn = psycopg2.connect('postgresql://postgres:root@localhost:5432/drons')
    cursor = conn.cursor()
    fId=flightId
    cursor.execute('SELECT dataid FROM values WHERE flightid=%s',(fId,))
    sensorD_ids=cursor.fetchall()
    #sensorD_ids=[1,2]
    cursor.close()
    conn.close()
    return sensorD_ids


def getDataSensorsOfFlight(dataId):
    conn = psycopg2.connect('postgresql://postgres:root@localhost:5432/drons')
    cursor = conn.cursor()
    cursor.execute('SELECT * FROM sensor_datas JOIN sensors ON (sensor_datas.sensorid=sensors.sensorid) WHERE dataId =ANY(%s)',(dataId,))
    sensorDatas_arr=[]
    sensorDatas_records=cursor.fetchall()
    for row in sensorDatas_records:
        sensor=Sensor(row[6],row[7])
        sensor_data=SensorData(row[0],row[2],row[3],row[4],row[5])
        sensor_data.setSensor(sensor)
        sensor_data.setLogName("circuit_breaker_flight_termination_disabled")
        sensorDatas_arr.append(sensor_data)
    cursor.close()
    conn.close()
    return sensorDatas_arr








def getValues(datas_arr):
    value_arr=[]
    ulog = ULog("./test.ulg", None, False)
    data = ulog.data_list
    header = []
    result = {}
    data.sort(key=lambda x: x.name)
    maxlen = 0
    for d in data:
        for i in datas_arr:
            if(i.getLogName() in d.data):
                for j in range(len(d.data[i.getLogName()])):
                    v=Value(0,int(d.data[i.getLogName()][j]),int(d.data['timestamp'][j]))
                    v.setSensorData(i)
                    value_arr.append(v)
        names = [f.field_name for f in d.field_data]
        names.remove('timestamp')
        names.insert(0, 'timestamp')
        for head in names:
            if (d.name + '.' + head) not in header: header.append(d.name + '.' + head)
        if len(d.data['timestamp']) > maxlen:
            maxlen = len(d.data['timestamp'])
    return value_arr


        
def getValuesLog(data_arr):
    global order
    value_arr=[]
    with open("logs.txt", "r",encoding='utf-8') as data:
        dict = ast.literal_eval(data.read())
        for d in dict:
            for i in data_arr:
                if i.getDataName() in d:
                    order%=len(d[i.getDataName()])
                    print(order)
                    v=Value(0,str(d[i.getDataName()][order]),int(d['Время'][order]))
                    v.setSensorData(i)
                    value_arr.append(v)
    order+=1
    return value_arr
 


#    for d in data:
#       d_keys = [f.field_name for f in d.field_data]
#        d_keys.remove('timestamp')
#        d_keys.insert(0, 'timestamp')
#        for key in d_keys:
#           result[d.name + '.' + key] = d.data[key]


#for j in range(len(d[i.getDataName()])):
 #                       v=Value(0,str(d[i.getDataName()][j]),int(d['Время'][j]))
  #                      v.setSensorData(i)
   #                     value_arr.append(v)