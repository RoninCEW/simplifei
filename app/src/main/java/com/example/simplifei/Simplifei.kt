package com.example.simplifei

import android.content.ActivityNotFoundException
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

open class App(val pkg: String) {
    data object YouTube : App("com.google.android.youtube")
    data object Lithium : App("com.faultexception.reader")
}

@Composable
fun Simplifei() {
    val context = LocalContext.current

    val launch = { app: App ->
        val intent = context.packageManager.getLaunchIntentForPackage(app.pkg)
        try {
            context.startActivity(intent ?: throw ActivityNotFoundException())
        } catch (e: ActivityNotFoundException) {
            Log.e("Simplifei", "App not installed", e)
        }
    }

    val alpha: Float by animateFloatAsState(1f, label = "buttonAlpha")

    Box(
        Modifier
            .graphicsLayer(alpha = alpha)
            .clickable { launch(App.YouTube) }) {
        Icon(
            Icons.Default.SmartDisplay,
            contentDescription = "YouTube",
            Modifier.size(250.dp),
            tint = MaterialTheme.colorScheme.surface.copy(alpha = alpha)
        )
    }
    Box(
        Modifier
            .graphicsLayer(alpha = alpha)
            .clickable { launch(App.Lithium) }) {
        Icon(
            Icons.AutoMirrored.Filled.MenuBook,
            contentDescription = "Lithium",
            Modifier.size(250.dp),
            tint = MaterialTheme.colorScheme.surface.copy(alpha = alpha)
        )
    }
}

@Composable
fun SimplifeiLandscape() {
    QuickAccessPanel(listOf(), listOf())
    Row(
        Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Simplifei()
    }
}

@Composable
fun SimplifeiPortrait() {
    QuickAccessPanel(listOf(), listOf())
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Simplifei()
    }
}