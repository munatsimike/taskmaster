package com.example.taskmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.taskflow.nagivation.bottomNav.BottomNavBar
import com.example.taskmaster.navigation.NavHost.AppNavHost
import com.example.taskmaster.ui.theme.TaskMasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            val navHost = rememberNavController()

            TaskMasterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavBar(navHost) },
                    content = { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            AppNavHost(navHost)
                        }
                    })
            }
        }
    }
}