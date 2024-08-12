package com.example.simplifei

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalConfiguration
import com.example.simplifei.ui.theme.SimplifeiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        startActivity(Intent(this, OotActivity::class.java))
        setContent {
            SimplifeiTheme {
                when (LocalConfiguration.current.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> {
                        SimplifeiPortrait()
                    }

                    Configuration.ORIENTATION_LANDSCAPE -> {
                        SimplifeiLandscape()
                    }

                    else -> SimplifeiLandscape()
                }
            }
        }
    }
}
