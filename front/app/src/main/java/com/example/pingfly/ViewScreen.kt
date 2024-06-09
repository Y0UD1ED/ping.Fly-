//#####################################################################################################################
//#####################################################################################################################
//###############################           Экран настройки контроллера                 ###############################
//#####################################################################################################################
//####    Автор: Кулишов Илья Вячеславович    #########################################################################
//####    Версия: v.0.0.1.0                   #########################################################################
//####    Дата: 07.06.2024                    #########################################################################
//#####################################################################################################################
//#####################################################################################################################

package com.example.pingfly

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
var viewElementTek = mutableStateOf(0)
var viewScreen = mutableStateOf(0)
var bigState= mutableStateOf(0)

//=====================================================================================
//Экран настройки контроллера
//=====================================================================================
@Composable
fun setScreenScreen(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            themes[themesTek.value].backroundColor
        ))
    if(viewScreen.value==0){
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                viewScreen.value = 2
                bigState.value = 1
            }, contentAlignment = Alignment.Center){
            if(viewBigBlock.status==false) {
                Image(
                    painter = painterResource(id = R.drawable.plussensorbig),
                    contentDescription = ""
                )
            }
            else{
                if(viewBigBlock.sensorDataId==1){
                    mapBlock()
                }
                if(viewBigBlock.sensorDataId==2){
                    cameraBlock()
                }

            }
        }
        Box {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(70.dp),
            ) {
                items(viewElementsList.value){
                    x-> if(x.id<6){
                        viewMiniBox(id = x.id)
                }
                }
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier
                        .padding(end = 10.dp, top = 10.dp)
                        .width(200.dp)
                        .height(200.dp)
                        .border(
                            width = 3.dp,
                            themes[themesTek.value].corpColor,
                            shape = RoundedCornerShape(10)
                        )
                        .clickable {
                            viewScreen.value = 2
                            bigState.value = 0
                        }, contentAlignment = Alignment.Center){
                        if(viewMediumBlock.status==false) {
                            Image(
                                painter = painterResource(id = R.drawable.plussensorbig),
                                contentDescription = ""
                            )
                        }
                        else{
                            if(viewMediumBlock.sensorDataId==1){
                                mapBlock()
                            }
                            if(viewMediumBlock.sensorDataId==2){
                                cameraBlock()
                            }

                        }

                    }

                }
            }
            Box(modifier = Modifier.fillMaxSize().padding(top=250.dp), contentAlignment = Alignment.TopEnd){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(70.dp)

                        ) {
                            items(viewElementsList.value) { x ->
                                if (x.id > 5 && x.id < 10) {
                                    viewMiniBox(id = x.id)
                                }
                            }
                        }
                    }

            }
        }

    }
    if(viewScreen.value==1){
        addViewScreen()
    }
    if(viewScreen.value==2){
        bigBlocksViewadd()
    }

}


//=====================================================================================
//Блок отображение ячейки отображения информации
//=====================================================================================
@Composable
fun viewMiniBox(id:Int){
    Box(modifier = Modifier.clickable {
        viewElementTek.value=id
        viewScreen.value=1
    }) {
        var element = viewElementsList.value.find { it.id == id }
        if (element != null) {
            if (!element.status) {
                Box(
                    modifier = Modifier
                        .rotate(270F)
                        .width(100.dp)
                        .height(50.dp)
                        .border(
                            color = themes[themesTek.value].corpColor,
                            width = 3.dp,
                            shape = RoundedCornerShape(11)
                        ), contentAlignment = Alignment.Center
                ){
                    Image(painter = painterResource(id = R.drawable.plusview), contentDescription ="plus")
                }
            }else{
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                        , contentAlignment = Alignment.Center
                ){
                    Row {
                        var txtNew = sensorDataList.value.find { it.dataId==element.sensorDataId }
                        if(txtNew!=null) {
                            /*Text(
                                text = txtNew.dataName, style = TextStyle(
                                    color = themes[themesTek.value].secondColor,
                                    fontSize = 14.sp
                                ), modifier = Modifier.rotate(270F)
                            )*/

                            Text(
                                text = parseView(element.sensorDataId),
                                style = TextStyle(
                                    color = themes[themesTek.value].secondColor,
                                    fontSize = 14.sp
                                ), modifier = Modifier.rotate(270F)
                            )
                        }
                    }

                     }
            }
        }
    }
}


//=====================================================================================
//Экран добавление отображения
//=====================================================================================
@Composable
fun addViewScreen() {
    Column(Modifier.padding(start=10.dp, end=10.dp)) {
        labelScreen(name = "Отображение")
        LazyColumn(Modifier.padding(top=5.dp)) {
            item {
                clearView()
            }
            items(sensorList){
                    sensor->
                if(vyborSensor.contains(sensor.sensorId)){
                    addSensorCardView(sensor = sensor)
                }
                            }
        }
    }
}


//=====================================================================================
//Карточка добавления отображения
//=====================================================================================
@SuppressLint("UnrememberedMutableState")
@Composable
fun addSensorCardView(sensor:Sensor){
    var flag= mutableStateOf(vyborSensor.contains(sensor.sensorId))
    var newEl=viewElementsList.value.find { it.id== viewElementTek.value }
    Box(modifier = Modifier
        .padding(top = 5.dp)
        .fillMaxWidth()
        .height(70.dp)
        .background(
            color = themes[themesTek.value].secondColor,
            shape = RoundedCornerShape(10)
        )
        .clickable {
            if (newEl != null) {
                newEl.sensorDataId = sensor.sensorId
                newEl.status = true
            }
            viewScreen.value = 0
        }, contentAlignment = Alignment.CenterStart){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = sensor.name, style = TextStyle(
                color = themes[themesTek.value].backroundColor,
                fontSize = 24.sp
            ), modifier = Modifier.padding(start = 15.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                Box(modifier = Modifier.padding(end = 15.dp)){
                        Image(painterResource(id = R.drawable.plussensor), contentDescription = "")

                }
            }
        }
    }
}


//=====================================================================================
//Блок добавления fullscreen отображения
//=====================================================================================
@Composable
fun bigBlocksViewadd(){
    Column(Modifier.padding(start=10.dp, end=10.dp)) {
        labelScreen(name = "Отображение")

        Box(modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .height(70.dp)
            .background(
                color = themes[themesTek.value].secondColor,
                shape = RoundedCornerShape(10)
            )
            .clickable {
                if (bigState.value == 0) {
                    if (viewMediumBlock.status == false) {
                        viewMediumBlock.status = true
                    } else {
                        viewMediumBlock.status = false
                    }
                    viewMediumBlock.sensorDataId = 1
                } else {
                    if (viewBigBlock.status == false) {
                        viewBigBlock.status = true
                    } else {
                        viewBigBlock.status = false
                    }
                    viewBigBlock.sensorDataId = 1
                }
                viewScreen.value = 0
            }, contentAlignment = Alignment.CenterStart){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Карта", style = TextStyle(
                    color = themes[themesTek.value].backroundColor,
                    fontSize = 24.sp
                ), modifier = Modifier.padding(start = 15.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                    Box(modifier = Modifier.padding(end = 15.dp)){
                        Image(painterResource(id = R.drawable.plussensor), contentDescription = "")

                    }
                }
            }
        }
        Box(modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .height(70.dp)
            .background(
                color = themes[themesTek.value].secondColor,
                shape = RoundedCornerShape(10)
            )
            .clickable {
                if (bigState.value == 0) {
                    if (viewMediumBlock.status == false) {
                        viewMediumBlock.status = true
                    } else {
                        viewMediumBlock.status = false
                    }
                    viewMediumBlock.sensorDataId = 2
                } else {
                    if (viewBigBlock.status == false) {
                        viewBigBlock.status = true
                    } else {
                        viewBigBlock.status = false
                    }
                    viewBigBlock.sensorDataId = 2
                }
                viewScreen.value = 0
            }, contentAlignment = Alignment.CenterStart){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Камера", style = TextStyle(
                    color = themes[themesTek.value].backroundColor,
                    fontSize = 24.sp
                ), modifier = Modifier.padding(start = 15.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                    Box(modifier = Modifier.padding(end = 15.dp)){
                        Image(painterResource(id = R.drawable.plussensor), contentDescription = "")

                    }
                }
            }
        }
    }
}

//=====================================================================================
//Блок видео
//=====================================================================================
@Composable
fun cameraBlock(){
    Image(painter = painterResource(id = R.drawable.camera), contentDescription ="Camera",
        contentScale = ContentScale.FillWidth, modifier = Modifier
            .rotate(270F)
            .scale(3F))
}

//=====================================================================================
//Блок карты
//=====================================================================================
@Composable
fun mapBlock(){
    //WebViewComponent("https://onlinescreenshare.com/#542036095")
}


//=====================================================================================
//Блок очистки отображения
//=====================================================================================
@Composable
fun clearView(){
    var newEl=viewElementsList.value.toList().find { it.id== viewElementTek.value }
    Box(modifier = Modifier
        .padding(top = 5.dp)
        .fillMaxWidth()
        .height(70.dp)
        .background(
            color = themes[themesTek.value].secondColor,
            shape = RoundedCornerShape(10)
        )
        .clickable {
            if (newEl != null) {
                newEl.status = false
            }
            viewScreen.value = 0
        }, contentAlignment = Alignment.Center){
        Text(text = "Очистить", style = TextStyle(
            color = themes[themesTek.value].backroundColor,
            fontSize = 24.sp
        ), modifier = Modifier.padding(start = 15.dp))
    }

}

//=====================================================================================
//Функции формирования отображения данных с датчиков
//Входные данные:
//                      idSensor:Int   - Id датчика
//Возращаемое значение:
//                      ret:String     - строка
//=====================================================================================
fun parseView(idSensor:Int):String{
    var ret="null"
    var sensorData= sensorDataList.value.find { it.sensorId==idSensor }
    if(sensorData!=null){
        ret=""
        if(sensorData.dataType=="int"){
           ret+=sensorData.sensorInt
            ret+=" "
           ret+=sensorData.UoM
        }
        if(sensorData.dataType=="float"){
            ret+=sensorData.sensorFloat
            ret+=" "
            ret+=sensorData.UoM
        }

        if(sensorData.dataType=="string"){
            ret+=sensorData.sensorString
            ret+=" "
            ret+=sensorData.UoM
        }
    }
    return ret

}