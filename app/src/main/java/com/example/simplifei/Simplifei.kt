package com.example.simplifei

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CallMissedOutgoing
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.SmartDisplay
import androidx.compose.material.icons.rounded.TravelExplore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

data class App(val name: String, val pkg: String, val icon: ImageVector)

val APPS = listOf(
    App("Youtube", "com.google.android.youtube", Icons.Rounded.SmartDisplay),
    App("Lithium", "com.faultexception.reader", Icons.AutoMirrored.Rounded.MenuBook)
)

@Suppress("DEPRECATION")
val DRAWER_APPS = listOf(
    App("Chrome", "com.android.chrome", Icons.Rounded.TravelExplore),
    App("Amazon Prime Video", "com.amazon.avod.thirdpartyclient", Icons.Rounded.CallMissedOutgoing),
    App("Netflix", "com.netflix.mediaclient", Icons.Rounded.Movie),
    App("Disney Plus", "com.disney.disneyplus", Icons.Rounded.Add)
)

@Composable
fun Simplifei(apps: List<App> = APPS) {
    val orientation = LocalConfiguration.current.orientation
    var isDrawerOpen by remember { mutableStateOf(false) }

    SettingsRow()

    if (isDrawerOpen) {
        AppDrawer(DRAWER_APPS) { isDrawerOpen = false }
    }

    val modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectVerticalDragGestures { change, dragAmount ->
                change.consume()
                if (dragAmount < -10) isDrawerOpen = true
            }
        }

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier,
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { AppList(apps) }
    } else {
        Row(
            modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) { AppList(apps) }
    }
}

@Composable
fun AppList(apps: List<App>, isAppDrawerList: Boolean = false) {
    val context = LocalContext.current
    val iconSize = if (isAppDrawerList) 100 else 250
    val color = if (isAppDrawerList) MaterialTheme.colorScheme.onSurface else Color.White
    val offset = IntOffset((0.025 * iconSize).toInt(), (0.025 * iconSize).toInt())
    return apps.forEach {
        Column(
            Modifier
                .clickable { launchApp(context, it) }
                .width(iconSize.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Icon(
                    it.icon, it.name,
                    Modifier
                        .size(iconSize.dp)
                        .offset { offset }
                        .blur(1.dp),
                    Color.Gray.copy(alpha = 0.5f)
                )
                Icon(it.icon, it.name, Modifier.size(iconSize.dp), color)
            }
            if (isAppDrawerList) Text(text = it.name, textAlign = TextAlign.Center, color = color)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppDrawer(apps: List<App>, closeDrawer: () -> Unit) {
    val bottomExtension = WindowInsets.navigationBars.getBottom(LocalDensity.current).dp
    ModalBottomSheet(
        onDismissRequest = closeDrawer,
        Modifier.offset(y = bottomExtension),
        scrimColor = Color.Transparent,
        dragHandle = { },
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
                .padding(bottom = bottomExtension),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AppList(apps, true)
        }
    }
}

fun launchApp(context: Context, app: App) {
    runCatching {
        val intent = context.packageManager.getLaunchIntentForPackage(app.pkg)
        context.startActivity(intent ?: throw ActivityNotFoundException())
    }.onFailure { Log.e("Simplifei", "App failed to launch", it) }
}
