package com.example.calculator6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.calculator6.ui.TableScreen
import com.example.calculator6.ui.theme.Calculator6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculator6Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TableScreen()
                }
            }
        }
    }
}