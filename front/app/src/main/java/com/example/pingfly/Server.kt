//#####################################################################################################################
//#####################################################################################################################
//###############################                Модуль связи с сервером                ###############################
//#####################################################################################################################
//####    Автор: Кулишов Илья Вячеславович    #########################################################################
//####    Версия: v.0.1.0.0                   #########################################################################
//####    Дата: 08.06.2024                    #########################################################################
//#####################################################################################################################
//#####################################################################################################################

package com.example.pingfly



import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import com.example.pingfly.sensorList as sensorList

data class SensorGet(
    val name: String,
    val sensorId: Int
)
var sensorGetList = listOf<Sensor>()


//=====================================================================================
//Запрос получения модулей
//=====================================================================================
fun newGetSensor() {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(adress+"/sensors")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    throw IOException("Запрос к серверу не был успешен:" +
                            " ${response.code} ${response.message}")
                }
                val myResponse = response.body?.string()
                val gson = Gson()
                val senList = object : TypeToken<List<Sensor>>() {}.type
                val senList2: List<Sensor> = gson.fromJson(myResponse, senList)
                sensorList.clear()
                for(x in senList2){
                    sensorList+=Sensor(x.name,x.sensorId)

                }
            }
        }
    })
}

//=====================================================================================
//Запрос получения записей сенсоров
//=====================================================================================
fun newGetSensorData() {
    val client = OkHttpClient()
    var transact = JSONObject()
    transact.put("flightID", 19)
    val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = transact.toString() .toRequestBody(mediaType)
    val request = Request.Builder()
        .url(adress+"/values?flight=19")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    throw IOException("Запрос к серверу не был успешен:" +
                            " ${response.code} ${response.message}")
                }
                val myResponse = response.body?.string()
                val gson = Gson()
                val senList = object : TypeToken<List<sensorDataGet>>() {}.type
                val senList2: List<sensorDataGet> = gson.fromJson(myResponse, senList)
                for(x in senList2){

                    var sensorDataSearch = sensorDataList.value.find { it.sensorId==x.sensorId }
                    if(sensorDataSearch!=null){
                        sensorDataSearch.timestamp=x.timestamp
                        if(sensorDataSearch.dataType=="float") sensorDataSearch.sensorFloat=x.value.toDouble()
                        if(sensorDataSearch.dataType=="int") sensorDataSearch.sensorInt=x.value.toInt()
                        if(sensorDataSearch.dataType=="string") sensorDataSearch.sensorString=x.value
                    }else{
                        var newS= SensorData(x.UoM, x.dataId, x.dataName, x.dataType,
                            x.sensorId,
                            x.timestamp, if(x.dataType=="int") x.value.toInt() else 0, x.value.toDouble(), x.value)
                        sensorDataList.value+=newS
                    }
                }
            }
        }
    })
}


//=====================================================================================
//Запрос получения точек
//=====================================================================================
fun newGetPointsData() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(adress+"/flight?id=47")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    throw IOException("Запрос к серверу не был успешен:" +
                            " ${response.code} ${response.message}")
                }
                val myResponse = response.body?.string()
                val gson = Gson()
                val senList = object : TypeToken<List<pointReq>>() {}.type
                val senList2: List<pointReq> = gson.fromJson(myResponse, senList)
                for(x in senList2){
                    planTek.value+=Plan(x.pos,1,x.oX,x.oY)
                }
            }
        }
    })
}

