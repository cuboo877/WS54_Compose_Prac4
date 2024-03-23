package com.example.composeprac4.widget

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun fastText(text:Any?, size:Int){
    Text(text = text.toString(), fontSize = size.sp)
}