import psycopg2

def addValues(values_arr,flightId):
    conn = psycopg2.connect('postgresql://postgres:root@localhost:5432/drons')
    cursor = conn.cursor()
    values=[]
    for i in values_arr:
        fId=flightId
        tmstmp=i.getTimestamp()
        v=i.getValue()
        sdId=i.getSensorData().getDataId()
        tmp=(fId,sdId,tmstmp,v,)
        values.append(tmp)
    cursor.executemany('INSERT INTO values(flightid,dataid,timestamp,value) values (%s,%s,%s,%s)',values)
    conn.commit()
    cursor.close()
    conn.close()

def addValue(value):
    conn = psycopg2.connect('postgresql://postgres:root@localhost:5432/drons')
    cursor = conn.cursor()
    cursor.execute('INSERT INTO values(flightid,dataid,timestamp,value) values (%s,%s,%s,%s)',value)
    conn.commit()
    cursor.close()
    conn.close()