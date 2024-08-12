package com.example.simplifei

import android.app.Activity
import android.graphics.PixelFormat
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.zIndex

//class AlwaysOnTop(private val context: Context) {
//    private var windowManager: WindowManager? = null
//    private var overlayView: View? = null
//    private var params: WindowManager.LayoutParams? = null
//
//    init {
//        showOverlay()
//    }
//
//    private fun showOverlay() {
//        params = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            PixelFormat.TRANSLUCENT
//        )
//
//        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        overlayView = layoutInflater.inflate(R.layout.full_screen_notification_layout, null)
//
//        params?.gravity = Gravity.START or Gravity.TOP
//        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//    }
//}

@Composable
fun AlwaysOnTop(setShow: (Boolean) -> Unit) {
    val window = (LocalView.current.context as Activity).window
    window.decorView.layoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        PixelFormat.TRANSLUCENT
    )

    return Row(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .zIndex(10f)
            .background(Color.Red.copy(0.1f))
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consume()
                    setShow(change.previousPosition.y < change.position.y)
                }
            }
    ) { }
}