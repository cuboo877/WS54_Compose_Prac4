package com.example.composeprac4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.example.composeprac4.service.SimpleNavController
import com.example.composeprac4.service.SimpleNavHost
import com.example.composeprac4.service.jsonToMap
import com.example.composeprac4.service.readJson
import com.example.composeprac4.ui.theme.ComposePrac4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val jsonString = readJson(this, "weatherData.json")
            val data = jsonToMap(jsonString)
            val navController = SimpleNavController(MainActivity.Screen.Choose)
            SimpleNavHost(navController = navController,modifier = Modifier.fillMaxSize(), data = data)
        }
    }

    enum class Screen{
        Choose,
        Home,
        Region
    }
}