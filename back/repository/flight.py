import psycopg2

def addFlight(oX,oY,height,sensorDataIds):
    conn = psycopg2.connect('postgresql://postgres:root@localhost:5432/drons')
    cursor = conn.cursor()
    flight_arr=[]
    flightId=1
    cursor.execute('INSERT INTO flights(oX,oY,height) VALUES (%s,%s,%s) RETURNING flightid',[oX,oY,height])
    flightId = cursor.fetchone()[0]
    for i in sensorDataIds:
        value=(flightId,i,0)
        flight_arr.append(value)
    print(flight_arr)
    cursor.executemany('INSERT INTO values(flightid,dataid,timestamp) VALUES (%s,%s,%s)',flight_arr)
    conn.commit()
    cursor.close()
    conn.close()

def getAverageDatasFlight(flightId):
    datas