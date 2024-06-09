//#####################################################################################################################
//#####################################################################################################################
//###############################                 экран контроллера                     ###############################
//#####################################################################################################################
//####    Автор: Кулишов Илья Вячеславович    #########################################################################
//####    Версия: v.0.0.0.1                   #########################################################################
//####    Дата: 09.06.2024                    #########################################################################
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.file.Files.find


var updateScreen = mutableStateOf(0)

//=====================================================================================
//Экран контроллера
//=====================================================================================
@SuppressLint("UnrememberedMutableState")
@Composable
fun flightScreen(){

     if(1==1){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    themes[themesTek.value].backroundColor
                )
        )
        if (viewScreen.value == 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                if (viewBigBlock.status == false) {

                } else {
                    if (viewBigBlock.sensorDataId == 1) {
                        mapBlock()
                    }
                    if (viewBigBlock.sensorDataId == 2) {
                        cameraBlock()
                    }

                }
            }
            Box {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(70.dp),
                ) {/*
                    items(viewElementsList.value) { x ->
                        if (x.id < 6) {
                            viewMiniBox1(id = x.id)
                        }
                    }*/
                    items(sensorDataList.value.toList()){
                        s-> Text(text = s.sensorFloat.toString(), style = TextStyle(
                            Color.White
                        ))
                    }
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        if (viewMediumBlock.status == false) {

                        } else {
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

                                }, contentAlignment = Alignment.Center
                            ) {
                                if (viewMediumBlock.sensorDataId == 1) {
                                    mapBlock()
                                }
                                if (viewMediumBlock.sensorDataId == 2) {
                                    cameraBlock()
                                }
                            }
                        }


                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 250.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {

                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(70.dp)

                            ) {
                                items(viewElementsList.value) { x ->
                                    if (x.id > 5 && x.id < 10) {
                                        viewMiniBox1(id = x.id)
                                    }
                                }
                            }

                    }

                }
            }

        }
    }
}


//=====================================================================================
//Экран ячейки контроллера
//=====================================================================================
@SuppressLint("UnrememberedMutableState")
@Composable
fun viewMiniBox1(id:Int){
    var element = mutableStateOf  ( viewElementsList.value.toList().find { it.id == id })
    var txtNew = mutableStateOf( sensorDataList.value.toList().find { it.sensorId== element.value!!.sensorDataId })


        element.value =   ( viewElementsList.value.toList().find { it.id == id })
        txtNew.value = sensorDataList.value.toList().find { it.sensorId== element.value!!.sensorDataId }


    Box() {

        if (element != null) {
            if (!element.value!!.status) {

            }else{
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                    , contentAlignment = Alignment.Center
                ){
                    Row {

                        if(txtNew!=null) {
                            /*Text(text = txtNew.dataName,style = TextStyle(
                                color = themes[themesTek.value].secondColor,
                                fontSize = 16.sp
                            ), modifier = Modifier.rotate(270F))*/
                            var txtn = mutableStateOf(parseView(element.value!!.sensorDataId))
                            txtn.value = (parseView(element.value!!.sensorDataId))
                            Text(text = txtNew.value?.sensorFloat.toString(),
                                style = TextStyle(
                                    color = themes[themesTek.value].secondColor,
                                    fontSize = 16.sp
                                ), modifier = Modifier.rotate(270F))
                        }

                    }

                }
            }
        }
    }
}