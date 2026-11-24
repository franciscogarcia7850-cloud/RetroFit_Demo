package com.example.retrofit_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.retrofit_demo.ui.PostListScreen
import com.example.retrofit_demo.ui.theme.RetroFitDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetroFitDemoTheme {
                PostListScreen()
            }
        }
    }
}
