//#####################################################################################################################
//#####################################################################################################################
//###############################                       Main                            ###############################
//#####################################################################################################################
//####    Автор: Кулишов Илья Вячеславович    #########################################################################
//####    Версия: v.0.1.0.0                   #########################################################################
//####    Дата: 07.06.2024                    #########################################################################
//#####################################################################################################################
//#####################################################################################################################


package com.example.pingfly

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pingfly.ui.theme.PingflyTheme
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            lifecycleScope.launch {
                newGetSensor()
                newGetPointsData()
                while(1==1) {
                    newGetSensorData()
                    delay(5000)
                }
            }

                        val client = OkHttpClient()


                Surface(color = themes[themesTek.value].backroundColor) {
                    Scaffold(
                        topBar = {},
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }, content = { padding ->
                            // Navhost: where screens are placed
                            NavHostContainer(navController = navController, padding = padding)
                        }
                    )
                }

        }
    }
}

//=====================================================================================
//BottomBar реализация
//=====================================================================================
@Composable
fun BottomNavigationBar(navController: NavController) {

    BottomNavigation(
        backgroundColor = themes[themesTek.value].backroundColor
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Constants.BottomNavItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                { Image(painter = painterResource(navItem.icon), contentDescription = navItem.label,
                    modifier = Modifier.scale(0.7F))},
                /*icon = {
                   Icon(painter = painterResource(id = navItem.icon), contentDescription = navItem.label,
                      tint =  themes[themesTek.value].corpColor, modifier = Modifier.scale(0.7F))
                },*/
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = false
            )
        }
        }
}
@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {

    NavHost(
        navController = navController,

        startDestination = "drone",

        modifier = Modifier.padding(paddingValues = padding),

        builder = {
            composable("drone") {
                droneScreen()
                droneScreen.value=0
            }


            composable("view") {
                setScreenScreen()
            }


            composable("plan") {
                planScreen()
            }
            composable("flight") {

                flightScreen()
            }
        })

}
