package com.example.simplifei

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.provider.Settings.ACTION_AUTO_ROTATE_SETTINGS
import android.provider.Settings.ACTION_BLUETOOTH_SETTINGS
import android.provider.Settings.ACTION_SETTINGS
import android.provider.Settings.ACTION_WIFI_SETTINGS
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.ScreenLockRotation
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

data class App(val name: String, val pkg: String, val icon: ImageVector)

val APPS = listOf(
    App("Youtube", "com.google.android.youtube", Icons.Default.SmartDisplay),
    App("Lithium", "com.faultexception.reader", Icons.AutoMirrored.Filled.MenuBook)
)

@Composable
fun Simplifei(apps: List<App> = APPS) {
    val orientation = LocalConfiguration.current.orientation

    SettingsRow()

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        return Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { AppList(apps) }
    }

    return Row(
        Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) { AppList(apps) }
}

@Composable
fun AppList(apps: List<App>) {
    val context = LocalContext.current
    return apps.forEach {
        Box(Modifier.clickable { launchApp(context, it) }) {
            Icon(
                it.icon, it.name,
                Modifier
                    .size(250.dp)
                    .offset(7.dp, 7.dp)
                    .blur(1.dp),
                Color.Gray.copy(alpha = 0.5f)
            )
            Icon(it.icon, it.name, Modifier.size(250.dp), Color.White)
        }
    }
}

data class Setting(val name: String, val action: String, val icon: ImageVector)

val SETTINGS = listOf(
    Setting("Wifi", ACTION_WIFI_SETTINGS, Icons.Default.Wifi),
    Setting("Bluetooth", ACTION_BLUETOOTH_SETTINGS, Icons.Default.Bluetooth),
    Setting("Screen Lock", ACTION_AUTO_ROTATE_SETTINGS, Icons.Default.ScreenLockRotation),
    Setting("Settings", ACTION_SETTINGS, Icons.Default.Settings)
)

@Composable
fun SettingsRow() {
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
    ) {
        SETTINGS.forEach {
            Box(Modifier.clickable { context.startActivity(Intent(it.action)) }) {
                Icon(it.icon, it.name, tint = Color.White)
            }
        }
    }
}

fun launchApp(context: Context, app: App) {
    runCatching {
        val intent = context.packageManager.getLaunchIntentForPackage(app.pkg)
        context.startActivity(intent ?: throw ActivityNotFoundException())
    }.onFailure { Log.e("Simplifei", "App failed to launch", it) }
}
