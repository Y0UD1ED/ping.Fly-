import psycopg2
from entities.sensor import Sensor
from entities.sensorData import SensorData

def getSensorDatasBySensorIds(sensorIds):
    sensorDatas_arr=[]
    conn = psycopg2.connect('postgresql://postgres:root@localhost:5432/drons')
    cursor = conn.cursor()

    cursor.execute('SELECT * FROM sensor_datas JOIN sensors ON (sensor_datas.sensorid=sensors.sensorid) WHERE dataId =ANY(%s)',(sensorIds,))
    
    sensorDatas_records=cursor.fetchall()
    for row in sensorDatas_records:
        sensor=Sensor(row[6],row[7])
        sensor_data=SensorData(row[0],row[2],row[3],row[4],row[5])
        sensor_data.setSensor(sensor)
        sensorDatas_arr.append(sensor_data)
    cursor.close()
    conn.close()
    return sensorDatas_arr