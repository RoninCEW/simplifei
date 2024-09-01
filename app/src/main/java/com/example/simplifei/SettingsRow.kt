package com.example.simplifei

import android.content.Intent
import android.provider.Settings.ACTION_AUTO_ROTATE_SETTINGS
import android.provider.Settings.ACTION_BLUETOOTH_SETTINGS
import android.provider.Settings.ACTION_SETTINGS
import android.provider.Settings.ACTION_WIFI_SETTINGS
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.ScreenLockRotation
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex


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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .zIndex(10f),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
    ) {
        SETTINGS.forEach {
            Box(Modifier.clickable { context.startActivity(Intent(it.action)) }) {
                Icon(it.icon, it.name, tint = Color.White)
            }
        }
    }
}
