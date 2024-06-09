//#####################################################################################################################
//#####################################################################################################################
//###############################           Экран планирования полетов                  ###############################
//#####################################################################################################################
//####    Автор: Кулишов Илья Вячеславович    #########################################################################
//####    Версия: v.0.0.1.0                   #########################################################################
//####    Дата: 09.06.2024                    #########################################################################
//#####################################################################################################################
//#####################################################################################################################

package com.example.pingfly

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.ui.Alignment

var planScreenState = mutableStateOf(0)


//=====================================================================================
//Экран планирования полета
//=====================================================================================
@Composable
fun planScreen(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(themes[themesTek.value].backroundColor))
    if(planScreenState.value==0) {
        //WebViewComponent("https://developer.android.com/develop/ui/compose/graphics/draw/overview")
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                    .background(
                        themes[themesTek.value].corpColor, shape =
                        RoundedCornerShape(10)
                    )
                    .clickable {
                        planScreenState.value = 1
                    }, contentAlignment = Alignment.Center
            ){
                Text(text = "Алгоритм", style = TextStyle(
                    fontSize = 24.sp,
                    color = themes[themesTek.value].secondColor
                ))
            }
        }
    }
    if(planScreenState.value==1){
        Column {
            labelScreen(name = "Алгоритм")
            //planTek.value.toList().sortBy { it.num }
            LazyColumn {
                item{
                    Box(modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(
                            color = themes[themesTek.value].secondColor,
                            shape = RoundedCornerShape(10)
                        )
                        .clickable {
                            //planTek.value=List<Plan>
                                   planScreenState.value=0
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Вернуться к карте", style = TextStyle(
                            color = themes[themesTek.value].backroundColor,
                            fontSize = 24.sp
                        ), modifier = Modifier.padding(start = 15.dp))
                    }
                }
                items(planTek.value.toList()){
                    p->
                    if(p.type==1){
                        Box(Modifier.padding(top=5.dp)){
                            coordBlock(plan = Plan(p.num,p.type,p.a,p.b))
                        }
                    }else{
                        Box(Modifier.padding(top=5.dp)){
                            upBlock(Plan(p.num,p.type,p.a,p.b))
                        }
                    }

                }
                item{
                    Box(modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(
                            color = themes[themesTek.value].secondColor,
                            shape = RoundedCornerShape(10)
                        )
                        .clickable {
                            var max=0
                                   for(x in planTek.value.toList()){
                                       if(x.num>max) max=x.num
                                   }
                            max++
                            planTek.value+=Plan(max,1,0.0,0.0)
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Добавить координату", style = TextStyle(
                            color = themes[themesTek.value].backroundColor,
                            fontSize = 24.sp
                        ), modifier = Modifier.padding(start = 15.dp))
                    }
                }
                item{
                    Box(modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(
                            color = themes[themesTek.value].secondColor,
                            shape = RoundedCornerShape(10)
                        )
                        .clickable {

                            var max=0
                            for(x in planTek.value.toList()){
                                if(x.num>max) max=x.num
                            }
                            max++

                            planTek.value+=Plan(max,2,0.0,0.0)
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Добавить высоту", style = TextStyle(
                            color = themes[themesTek.value].backroundColor,
                            fontSize = 24.sp
                        ), modifier = Modifier.padding(start = 15.dp))
                    }
                }
            }
        }
    }
}


//=====================================================================================
//Блок координаты
//=====================================================================================
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun coordBlock(plan:Plan){
    var x = mutableStateOf(plan.a)
    var y = mutableStateOf(plan.b)
    Box(modifier = Modifier
        .padding(top = 5.dp)
        .fillMaxWidth()
        .height(120.dp)
        .background(
            color = themes[themesTek.value].secondColor,
            shape = RoundedCornerShape(10)
        ).pointerInput(Unit){
            detectTapGestures  (
                onDoubleTap = {
                    val findt= planTek.value.toList().find { it.num==(plan.num-1) }
                    if(findt!=null){
                        findt.num++
                    }
                    val findt1= planTek.value.toList().find { it.num==(plan.num) }
                    if(findt1!=null){
                        findt1.num--
                    }
                },
                onLongPress = {
                    val findt= planTek.value.toList().find { it.num==(plan.num) }
                    if(findt!=null){
                        planTek.value-=findt
                    }
                }
            )
        }){
        Column {
            Text(text = "Лететь к:", style = TextStyle(
                color = themes[themesTek.value].backroundColor,
                fontSize = 24.sp
            ), modifier = Modifier.padding(top=15.dp,start = 15.dp))
            Row(
                Modifier
                    .padding(start = 15.dp, end = 10.dp, top = 5.dp)
                    .fillMaxWidth()){
                Box(
                    Modifier
                        .padding(end = 5.dp)
                        .weight(1f)){
                    TextField(
                        value = x.value.toString(),
                        onValueChange = { x.value = it.toDouble()
                                        plan.a=x.value},
                        label = { Text("X:") },
                        singleLine = true,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .height(50.dp)
                            .background(themes[themesTek.value].backroundColor, shape = RoundedCornerShape(20))
                            .border(
                                width = 2.dp,
                                shape = RoundedCornerShape(10.dp),
                                color = if (x.value.toString() != "") themes[themesTek.value].corpColor else Color.Gray
                            ),
                        shape = RoundedCornerShape(15.dp),
                        colors = textFieldColors(
                            unfocusedLabelColor = themes[themesTek.value].secondColor,
                            focusedLabelColor = themes[themesTek.value].secondColor,

                            unfocusedTextColor = themes[themesTek.value].secondColor,
                            focusedTextColor = themes[themesTek.value].secondColor,
                            containerColor = themes[themesTek.value].backroundColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent
                        )
                    )
                }
                Box(
                    Modifier
                        .padding(start = 10.dp, end=15.dp)
                        .weight(1f)){
                    TextField(
                        value = y.value.toString(),
                        onValueChange = { y.value = it.toDouble() 
                                        plan.b=y.value},
                        label = { Text("Y:") },
                        singleLine = true,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .height(50.dp)
                            .background(themes[themesTek.value].backroundColor, shape = RoundedCornerShape(20))
                            .border(
                                width = 2.dp,
                                shape = RoundedCornerShape(10.dp),
                                color = if (y.value.toString() != "") themes[themesTek.value].corpColor else Color.Gray
                            ),
                        shape = RoundedCornerShape(15.dp),
                        colors = textFieldColors(
                            unfocusedLabelColor = themes[themesTek.value].secondColor,
                            unfocusedTextColor = themes[themesTek.value].secondColor,
                            focusedTextColor = themes[themesTek.value].secondColor,
                            containerColor = themes[themesTek.value].backroundColor,
                            focusedLabelColor = themes[themesTek.value].secondColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }

}


//=====================================================================================
//Блок высоты
//=====================================================================================
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun upBlock(plan:Plan) {
   var plan1= mutableStateOf(plan)

    Box(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .height(115.dp)
            .background(
                color = themes[themesTek.value].secondColor,
                shape = RoundedCornerShape(10)
            ).pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        val findt = planTek.value.toList().find { it.num == (plan.num - 1) }
                        if (findt != null) {
                            findt.num++
                        }
                        val findt1 = planTek.value.toList().find { it.num == (plan.num) }
                        if (findt1 != null) {
                            findt1.num--
                        }
                    },
                    onLongPress = {
                        val findt = planTek.value.toList().find { it.num == (plan.num) }
                        if (findt != null) {
                            planTek.value-= findt
                        }
                    }
                )
            }
    ) {
        Column {
            Text(
                text = "Набрать высоту:", style = TextStyle(
                    color = themes[themesTek.value].backroundColor,
                    fontSize = 24.sp
                ), modifier = Modifier.padding(top=15.dp,start = 15.dp)
            )
            Box(modifier = Modifier.fillMaxWidth(0.5f)){
                TextField(
                    value = plan.a.toString(),
                    onValueChange = { plan.a = it.toDouble()
                        plan.a=plan.a},
                    label = { Text("Up:") },
                    singleLine = true,
                    modifier = Modifier
                        .padding(start = 15.dp,top = 5.dp)
                        .height(50.dp)
                        .background(color = themes[themesTek.value].backroundColor, shape = RoundedCornerShape(25))
                        .border(
                            width = 2.dp,
                            shape = RoundedCornerShape(10.dp),
                            color = if (plan.a.toString() != "") themes[themesTek.value].corpColor else Color.Gray
                        ),
                    shape = RoundedCornerShape(15.dp),
                    colors = textFieldColors(
                        unfocusedLabelColor = themes[themesTek.value].secondColor,
                        focusedLabelColor = themes[themesTek.value].secondColor,
                        unfocusedTextColor = themes[themesTek.value].secondColor,
                        focusedTextColor = themes[themesTek.value].secondColor,
                        containerColor = themes[themesTek.value].backroundColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )
            }
        }

    }

}


//=====================================================================================
//Отображение веб страницы
//=====================================================================================
@Composable
fun WebViewComponent(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                }
            }
            loadUrl(url)
        }
    }, modifier = Modifier.fillMaxSize())
}