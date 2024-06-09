import psycopg2

def addFlight(flightData):
    conn = psycopg2.connect('postgresql://postgres:root@localhost:5432/drons')
    cursor = conn.cursor()
    flight_arr=[]
    flightId=1
    oX=flightData[0]['oX']
    oY=flightData[0]['oY']
    height=flightData[0]['height']
    cursor.execute('INSERT INTO flights(oX,oY,height) VALUES (%s,%s,%s) RETURNING flightId',[oX,oY,height])
    #conn.commit()
    flightId = cursor.fetchone()[0]
    for i in range(len(flightData)):
        oX=flightData[i]['oX']
        oY=flightData[i]['oY']
        height=flightData[i]['height']
        print([flightId,oX,oY,height,i])
        cursor.execute('INSERT INTO route(flightId,oX,oY,height,pos) VALUES(%s,%s,%s,%s,%s)',[flightId,oX,oY,height,i])
    conn.commit()
    cursor.close()
    conn.close()
    return flightId

def getRouteForFlight(flightId):
    route_arr=[]
    conn = psycopg2.connect('postgresql://postgres:root@localhost:5432/drons')
    cursor = conn.cursor()
    cursor.execute('SELECT * FROM route WHERE flightid=%s ORDER BY pos',(flightId,))
    route_arr=cursor.fetchall()
    res=[]
    for i in route_arr:
        dict={
            'pos':i[2],
            'oX':i[3],
            'oY':i[4],
            'height':i[5]
        }
        res.append(dict)
    cursor.close()
    conn.close()
    return res

def addSensorDataForSimpleFlight(sensorIds):
    conn = psycopg2.connect('postgresql://postgres:root@localhost:5432/drons')
    cursor = conn.cursor()
    flight_arr=[]
    flightId=1
    print(tuple([sensorIds]))
    cursor.execute('SELECT dataid FROM sensor_datas WHERE sensorid=ANY(%s)',tuple([sensorIds]))
    dataIds=cursor.fetchall()
    print(dataIds)
    cursor.execute('INSERT INTO flights(oX,oY,height) VALUES (%s,%s,%s) RETURNING flightId',[0,0,0])
    flightId = cursor.fetchone()[0]
    for i in dataIds:
        value=(flightId,i,0)
        flight_arr.append(value)
    cursor.executemany('INSERT INTO values(flightid,dataid,timestamp) VALUES (%s,%s,%s)',flight_arr)
    conn.commit()
    cursor.close()
    conn.close()
    return flightId


#for i in sensorDataIds:
#        value=(flightId,i,0)
#        flight_arr.append(value)
#    cursor.executemany('INSERT INTO values(flightid,dataid,timestamp) VALUES (%s,%s,%s)',flight_arr)
    