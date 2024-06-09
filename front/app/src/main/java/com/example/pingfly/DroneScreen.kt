//#####################################################################################################################
//#####################################################################################################################
//###############################                Экран сборки дрона                     ###############################
//#####################################################################################################################
//####    Автор: Кулишов Илья Вячеславович    #########################################################################
//####    Версия: v.0.1.0.0                   #########################################################################
//####    Дата: 07.06.2024                    #########################################################################
//#####################################################################################################################
//#####################################################################################################################

package com.example.pingfly

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

var droneScreen = mutableStateOf(0)
var drawState= mutableStateOf(true)

//=====================================================================================
//Экран сборки дрона
//=====================================================================================
@Composable
fun droneScreen(){

    if(droneScreen.value==0) {
        Box(
            Modifier
                .fillMaxSize()
                .background(themes[themesTek.value].backroundColor)
        ) {
            Column(Modifier.padding(start = 10.dp, end = 10.dp)) {
                nameDroneBlock("Карлсон")
                var newSensorList = listOf<Sensor>()
                for (x in sensorList) {
                    if (vyborSensor.contains(x.sensorId)) newSensorList += x
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.padding(top = 15.dp)
                ) {
                    items(newSensorList) { sensor ->
                        sensorCardDroneScreen(sensor = sensor)


                    }
                    item {
                        addSensorDroneScreen()
                    }
                }
            }
        }
    }
    if(droneScreen.value==1&& drawState.value){
        addSensorScreen()
    }
}



//=====================================================================================
//Блок отображения текущего дрона
//=====================================================================================
@Composable
fun nameDroneBlock(name:String){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .background(color = themes[themesTek.value].corpColor, shape = RoundedCornerShape(10)),
        contentAlignment = Alignment.Center){
        Row(verticalAlignment = Alignment.CenterVertically){
            Image( painterResource(id = R.drawable.dronebig), contentDescription = "Drone")
            Text(text = name, style = TextStyle(
                fontSize = 32.sp,
                color = themes[themesTek.value].secondColor
            ), modifier = Modifier.padding(start=30.dp))
        }
    }
}


//=====================================================================================
//Блок отображения сенсора
//=====================================================================================
@Composable
fun sensorCardDroneScreen(sensor:Sensor){
    Box(modifier = Modifier
        .width(150.dp)
        .height(150.dp)
        .background(
            color = themes[themesTek.value].secondColor,
            shape = RoundedCornerShape(10)
        )
        , contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painterResource(id = R.drawable.hug), contentDescription = "")
            Text(text = sensor.name, style = TextStyle(
               color= themes[themesTek.value].backroundColor,
                fontSize = 16.sp
            ), modifier = Modifier.padding(top=5.dp))
        }
    }

}


//=====================================================================================
//Блок добавления модуля
//=====================================================================================
@Composable
fun addSensorDroneScreen(){
    Box(modifier = Modifier.fillMaxSize().background(color=themes[themesTek.value].backroundColor))
    Box(modifier = Modifier
        .width(150.dp)
        .height(150.dp)
        .background(
            color = themes[themesTek.value].corpColor,
            shape = RoundedCornerShape(10)
        )
        .clickable {
            droneScreen.value = 1
        }
        , contentAlignment = Alignment.Center){
        Image(painterResource(id = R.drawable.plus), contentDescription = "")
    }
}


//=====================================================================================
//Блок заголовков
//=====================================================================================
@Composable
fun labelScreen(name:String){
    Box(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                color = themes[themesTek.value].corpColor,
                shape = RoundedCornerShape(10)
            ), contentAlignment = Alignment.CenterStart){
        Text(text = name, style = TextStyle(
            color= themes[themesTek.value].secondColor,
            fontSize = 32.sp
        ), modifier = Modifier.padding(start = 40.dp))
    }
}


//=====================================================================================
//Экран добавления модулей
//=====================================================================================
@Composable
fun addSensorScreen(){
    Column(modifier = Modifier.padding(start=10.dp, end=10.dp, top=25.dp)) {
        labelScreen(name = "Модули")
        LazyColumn {
            items(sensorList){
                sensor->
                addSensorCard(sensor = sensor)
            }
        }
    }
}


//=====================================================================================
//Карточка добавления модуля
//=====================================================================================
@SuppressLint("UnrememberedMutableState")
@Composable
fun addSensorCard(sensor:Sensor){
    var flag= mutableStateOf(vyborSensor.contains(sensor.sensorId))
    Box(modifier = Modifier
        .padding(top = 5.dp)
        .fillMaxWidth()
        .height(70.dp)
        .background(
            color = themes[themesTek.value].secondColor,
            shape = RoundedCornerShape(10)
        )
        .clickable {
            drawState.value=false
            if (vyborSensor.contains(sensor.sensorId)) {
                var iterator1 = vyborSensor.iterator()

                while (iterator1.hasNext()) {
                    val sensor1 = iterator1.next()
                    if (sensor1 == sensor.sensorId) iterator1.remove()
                }
                sensorList = sensorList.toMutableList()
            } else {
                vyborSensor += sensor.sensorId
            }
            drawState.value=true
            flag.value=vyborSensor.contains(sensor.sensorId)

        }, contentAlignment = Alignment.CenterStart){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = sensor.name, style = TextStyle(
                color = themes[themesTek.value].backroundColor,
                fontSize = 24.sp
            ), modifier = Modifier.padding(start = 15.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                Box(modifier = Modifier.padding(end = 15.dp)){
                    if(flag.value){
                        Image(painterResource(id = R.drawable.deletesensor), contentDescription = "")
                    }
                    else{
                        Image(painterResource(id = R.drawable.plussensor), contentDescription = "")
                    }
                }
            }
        }
    }
}