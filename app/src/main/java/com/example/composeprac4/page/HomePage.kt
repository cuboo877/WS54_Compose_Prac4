package com.example.composeprac4.page

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeprac4.service.SimpleNavController
import com.example.composeprac4.service.getIconIdByDescription
import com.example.composeprac4.service.getTranslated
import com.example.composeprac4.widget.NavDrawerContent
import com.example.composeprac4.widget.fastText
import com.example.composeprac4.widget.homeAppBar

object HomePage {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun build(navController: SimpleNavController, data:Map<String, Any>){
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val current = data.get("current") as Map<String,Any>
        ModalDrawer(drawerContent = {NavDrawerContent.build(
            scope = scope,
            drawerState = drawerState,
            navController = navController,
            previousData = data
        )}, drawerState = drawerState, content = {
            Scaffold(topBar = { homeAppBar(title = current["name"].toString(), scope = scope, drawerState = drawerState)}){
                val lazyListState = rememberLazyListState()
                LazyColumn(state = lazyListState, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        tempColumn(current)
                        Spacer(modifier = Modifier.height(20.dp))
                        hourlyTempDisplay(data)
                        Spacer(modifier = Modifier.height(20.dp))
                        DailyDataDisplay(data)
                        Spacer(modifier = Modifier.height(20.dp))
                        pm25Display(current)
                    }
                }
            }
        })
    }

    @Composable
    fun tempColumn(data:Map<String, Any>){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            fastText(text = "${data.get("temp_c")}°C", size = 40)
            fastText(text = "${data.get("maxTemp_c")}°C / ${data.get("minTemp_c")}°C", size = 40)
            fastText(text = getTranslated(value = data.get("description")), size = 25)
        }
    }

    @Composable
    fun hourlyTempDisplay(data:Map<String, Any>){
        val forecastData = data.get("forecast") as Map<String, Any>
        val hourlyData = forecastData.get("hourly") as List< Map<String, Any>>
        val lazyListState = rememberLazyListState()
        LazyRow(state = lazyListState, modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(150.dp)
            .clip(
                RoundedCornerShape(35.dp)
            )
            .background(Color.LightGray), horizontalArrangement = Arrangement.Center){
            items(hourlyData.size){
                _hourlyDataColumn(hourlyData[it])
            }
        }
    }

    @Composable
    fun _hourlyDataColumn(data:Map<String, Any>){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(15.dp)) {
            fastText(text = data.get("time"), size = 20)
            Image(painter = painterResource(id = getIconIdByDescription(data.get("description").toString())), contentDescription =null, Modifier.size(40.dp))
            fastText(text = data.get("daily_chance_of_rain"), size = 20)
            fastText(text = "${data.get("temp_c")}°C", size = 20)
        }
    }

    @Composable
    fun DailyDataDisplay(data:Map<String, Any>){
        val forecastData = data.get("forecast") as Map<String, Any>
        val hourlyData = forecastData.get("day") as List< Map<String, Any>>
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clip(RoundedCornerShape(35.dp))
            .background(Color.LightGray), horizontalAlignment = Alignment.CenterHorizontally) {
            hourlyData.forEach(){
                Spacer(modifier = Modifier.height(15.dp))
                _dailyDataRow(it)

            }
        }
    }

    @Composable
    fun _dailyDataRow(data:Map<String, Any>){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            fastText(text = data.get("date"), size = 20)
            Image(painter = painterResource(id = getIconIdByDescription(data.get("description").toString())), contentDescription =null, Modifier.size(50.dp))
            fastText(text = "${data.get("daily_chance_of_rain")}%", size = 20)
            fastText(text = "${data.get("maxTemp_c")}°C / ${data.get("minTemp_c")}°C", size = 20)
        }
    }

    @Composable
    fun pm25Display(data:Map<String,Any>){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clip(RoundedCornerShape(35.dp))
            .background(
                Color.LightGray
            ), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
            fastText(text = "PM2.5", size = 20)
            Divider(Modifier.fillMaxWidth(0.9f), thickness = 3.dp)
            Text(text = data.get("pm25").toString(), fontSize = 50.sp)
        }
    }
}

