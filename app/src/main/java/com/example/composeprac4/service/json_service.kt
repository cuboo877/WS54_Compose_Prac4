package com.example.composeprac4.service

import android.content.Context
import androidx.compose.runtime.Composable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun readJson(context: Context, fileName:String):String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

fun jsonToMap(jsonString:String):Map<String,Any>{
    val gson = Gson()
    val mapType = object: TypeToken<Map<String,Any>>(){}.type
    return gson.fromJson(jsonString,mapType)
}