package com.example.composeprac4.page

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.composeprac4.service.SimpleNavController
import com.example.composeprac4.widget.BackAppBar
import com.example.composeprac4.R
import com.example.composeprac4.service.getTranslated
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

object RegionPage {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun build(navController: SimpleNavController, previousData:Map<String,Any>, data:Map<String,Any>){
        val openDialog = remember {
            mutableStateOf(false)
        }

        val cityNameList = data.keys.toList()
        var unselect = remember {
            mutableStateOf(cityNameList)
        }
        var select = remember {
            mutableStateOf(listOf<String>())
        }
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = { BackAppBar.build(navController, previousData)},
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { openDialog.value = true },

                    ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    val lazyListState = rememberLazyListState()
                    if(openDialog.value){
                        AlertDialog(
                            title = {Text(text = stringResource(id = R.string.add_a_region))},
                            onDismissRequest = { openDialog.value = false },
                            confirmButton = {},
                            text = {
                                LazyColumn(state = lazyListState){
                                    items(unselect.value.size){
                                        val name = unselect.value[it] // !!!!!!
                                        Text(
                                            text = getTranslated(value = name),
                                            modifier = Modifier.clickable {
                                                unselect.value = unselect.value.minus(name)
                                                select.value = select.value.plus(name)
                                                openDialog.value = false
                                            }
                                        )
                                    }

                                }
                            }
                        )
                    }
                }
            }
        ) {
            val userAgent = "test"
            Configuration.getInstance().userAgentValue = userAgent
            val cityDataList = remember {
                mutableStateListOf<regionData>()
            }
            select.value.forEach() {
                val cityData = data[it] as Map<String, Any>
                val cityCurrentData = cityData["current"] as Map<String, Any>
                cityDataList.add(
                    regionData(
                        name = getTranslated(value = cityCurrentData["name"].toString()),
                        lat = cityCurrentData["lat"] as Double,
                        lon = cityCurrentData["lon"] as Double,
                        description = getTranslated(value = cityCurrentData["description"].toString()),
                        rain = (cityCurrentData["daily_chance_of_rain"] as Double).toInt(),
                        temp = (cityCurrentData["temp_c"] as Double).toInt()
                    )
                )
            }
// java.lang.ClassCastException: java.lang.Double cannot be cast to java.lang.Integer
//        at
            var geoPoint = remember {mutableStateOf(GeoPoint(23.5,121.5))}
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                        context ->
                    MapView(context).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setCenter(geoPoint.value)
                        controller.setZoom(9.5)
                    }

                },
                update = {
                        view ->
                    view.controller.setCenter(geoPoint.value)
                    for(data in cityDataList){
                        val marker = Marker(view)
                        marker.position = GeoPoint(data.lat,data.lon)
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        marker.title = data.name
                        marker.snippet = "${data.temp} / ${data.description} / ${data.rain}%"
                        view.overlays.add(marker)
                        marker.setOnMarkerClickListener{
                                it, view ->
                            it.showInfoWindow()
                            true
                        }
                    }
                })
        }
    }
}
data class regionData(
    val name:String,
    val lat:Double,
    val lon:Double,
    val description:String,
    val rain:Int,
    val temp:Int
)