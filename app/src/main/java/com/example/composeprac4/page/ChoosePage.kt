package com.example.composeprac4.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeprac4.MainActivity
import com.example.composeprac4.service.SimpleNavController
import com.example.composeprac4.service.getTranslated

object ChoosePage {
    @Composable
    fun build(navController: SimpleNavController, data:Map<String,Any>){
        val lazyListState = rememberLazyListState()
        val cityDataList = data.values.toList()
        LazyColumn(state = lazyListState, modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(15.dp)){
            items(cityDataList.size){
                Spacer(modifier = Modifier.height(20.dp))
                chooseRow(navController,cityDataList[it] as Map<String,Any>)
            }
        }
    }

    @Composable
    fun chooseRow(navController: SimpleNavController, data:Map<String,Any>){
        val currentData = data.get("current") as Map<String,Any>
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .clip(RoundedCornerShape(35.dp))
            .background(color = Color.LightGray)
            .clickable { navController.push(MainActivity.Screen.Home, data) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = getTranslated(value = currentData.get("name")), fontSize = 30.sp)
        }
    }
}