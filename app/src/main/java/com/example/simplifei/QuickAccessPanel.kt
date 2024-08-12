package com.example.simplifei

import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.simplifei.ui.theme.SimplifeiTheme
import kotlin.math.roundToInt

val gap = 36.dp

@Composable
fun QuickAccessPanel(wifiConnects: List<String>, btConnects: List<String>) {
    var show by remember { mutableStateOf(false) }

    if (!show) {
        return AlwaysOnTop { show = it }
    }

    Row(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim)
            .padding(gap)
            .zIndex(10f)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    show = !(change.previousPosition.y > change.position.y)
                }
            },
        horizontalArrangement = Arrangement.spacedBy(gap)
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(gap)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clip(RoundedCornerShape(gap))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(gap)
                    .align(Alignment.End)
            ) {
                Icon(
                    Icons.Default.Wifi,
                    "Volume",
                    Modifier
                        .fillMaxSize(0.2f)
                        .align(Alignment.TopEnd)
                        .offset(32.dp, 0.dp)
                )
                LazyColumn {
                    items(wifiConnects) { wifi ->
                        Text(wifi, Modifier.fillMaxWidth(), fontSize = 36.sp)
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clip(RoundedCornerShape(gap))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(gap)
                    .align(Alignment.End)
            ) {
                Icon(
                    Icons.Default.Bluetooth,
                    "Volume",
                    Modifier
                        .fillMaxSize(0.2f)
                        .align(Alignment.TopEnd)
                        .offset(32.dp, 0.dp)
                )
                LazyColumn {
                    items(btConnects) { bt ->
                        Text(bt, Modifier.fillMaxWidth(), fontSize = 36.sp)
                    }
                }
            }
        }
        Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(gap)) {
            BrightnessSlider()
            VolumeSlider()
        }
    }
}

@Composable
fun RowScope.BrightnessSlider() {
    var sliderHeight by remember { mutableIntStateOf(1) }
    var fill by remember { mutableFloatStateOf(0f) }

    Box(
        Modifier
            .fillMaxHeight()
            .onSizeChanged { sliderHeight = it.height }
            .weight(1f)
            .clip(RoundedCornerShape(gap))
            .align(Alignment.Bottom)
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    change.consume()
                    fill += dragAmount
                }
            }
            .pointerInput(Unit) {
                detectTapGestures { fill = it.y }
            }
    ) {
        Icon(
            Icons.Default.Brightness4,
            "Brightness",
            Modifier
                .fillMaxSize(1 / 3f)
                .zIndex(3f)
                .offset(0.dp, 25.dp)
                .align(Alignment.BottomCenter)
        )

        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceTint),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {}

        Column(
            Modifier
                .fillMaxHeight((sliderHeight - fill) / sliderHeight)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {}
    }
}

@Composable
fun setVolume(fraction: Float) {
    val am = LocalContext.current.getSystemService(AUDIO_SERVICE) as AudioManager
    val percentage = (fraction * am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)).roundToInt()
    am.setStreamVolume(
        AudioManager.STREAM_MUSIC,
        percentage,
        AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE
    )
}

@Composable
fun RowScope.VolumeSlider() {
    var sliderHeight by remember { mutableIntStateOf(1) }
    var fill by remember { mutableFloatStateOf(0f) }
    setVolume((sliderHeight - fill) / sliderHeight)

    Box(
        Modifier
            .fillMaxHeight()
            .onSizeChanged { sliderHeight = it.height }
            .weight(1f)
            .clip(RoundedCornerShape(gap))
            .align(Alignment.Bottom)
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    change.consume()
                    fill += dragAmount
                }
            }
            .pointerInput(Unit) { detectTapGestures { fill = it.y } }
    ) {
        Icon(
            Icons.AutoMirrored.Filled.VolumeUp,
            "Volume",
            Modifier
                .fillMaxSize(1 / 3f)
                .zIndex(3f)
                .offset(0.dp, 25.dp)
                .align(Alignment.BottomCenter)
        )

        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceTint),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {}
        Column(
            Modifier
                .fillMaxHeight((sliderHeight - fill) / sliderHeight)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {}
    }
}

@Preview(device = "spec:width=1200px,height=1980px,dpi=240,orientation=landscape")
@Composable
fun QuickAccessPanelPreview() {
    SimplifeiTheme {
        QuickAccessPanel(
            listOf(
                "Wifi 1", "Wifi 2", "Wifi 3",
                "Wifi 1", "Wifi 2", "Wifi 3",
                "Wifi 1", "Wifi 2", "Wifi 3",
                "Wifi 1", "Wifi 2", "Wifi 3",
                "Wifi 1", "Wifi 2", "Wifi 3",
                "Wifi 1", "Wifi 2", "Wifi 3"
            ),
            listOf(
                "Bluetooth 1", "Bluetooth 2", "Bluetooth 3",
                "Bluetooth 1", "Bluetooth 2", "Bluetooth 3",
                "Bluetooth 1", "Bluetooth 2", "Bluetooth 3",
                "Bluetooth 1", "Bluetooth 2", "Bluetooth 3",
                "Bluetooth 1", "Bluetooth 2", "Bluetooth 3"
            )
        )
    }
}