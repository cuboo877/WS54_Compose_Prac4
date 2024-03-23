package com.example.composeprac4.service

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.composeprac4.R

@Composable
fun getTranslated(value :Any?): String {
    val maps = mapOf(
        "sunny" to stringResource(id = R.string.sunny),
        "rainy" to stringResource(id = R.string.heavy_rain),
        "cloudy" to stringResource(id = R.string.cloudy),
        "Taipei" to stringResource(id = R.string.taipei),
        "Taichung" to stringResource(id = R.string.current_location),
        "Ayo" to stringResource(id = R.string.ayo),
        "Yee" to stringResource(id = R.string.yee),
        "TestCity" to stringResource(id = R.string.testcity)
    )
    return maps[value.toString()]!!
}

@Composable
fun getIconIdByDescription(value:String):Int{
    val maps = mapOf(
        "sunny" to R.drawable.morning_sun_sunrise_icon,
        "rainy" to R.drawable.weather_clouds_rain_storm_thunder_icon,
        "cloudy" to R.drawable.clouds_night_storm_icon,
    )
    return maps[value]!!
}