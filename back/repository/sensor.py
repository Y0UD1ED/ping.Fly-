import psycopg2
from entities.sensor import Sensor

def getAllSensors():
    sensor_arr=[]
    conn = psycopg2.connect('postgresql://postgres:root@localhost:5432/drons')
    cursor = conn.cursor()

    cursor.execute('SELECT * FROM sensors')
    data=cursor.fetchall()
    for row in data:
        s=Sensor(row[0],row[1])
        sensor_arr.append(s)
    cursor.close()
    conn.close()
    return sensor_arr