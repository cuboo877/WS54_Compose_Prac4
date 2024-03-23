package com.example.composeprac4.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.composeprac4.service.SimpleNavController
import com.example.composeprac4.service.getTranslated
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun homeAppBar(title:String,scope:CoroutineScope, drawerState: DrawerState){
    TopAppBar(title = { Row() {
        Text(text = getTranslated(value = title))
        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
    }}, navigationIcon = { IconButton(onClick = {scope.launch { drawerState.open() }}) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Color.White)

    }})
}