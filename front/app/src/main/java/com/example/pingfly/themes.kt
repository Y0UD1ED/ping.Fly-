package com.example.pingfly

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class Theme(var backroundColor: Color, var secondColor:Color, var corpColor:Color, var fieldColor:Color)

var themes = listOf(
    Theme(Color(80, 80, 80), Color(255, 255, 255), Color(75, 86, 117), Color(217, 217, 217))
)
var themesTek = mutableStateOf(0)