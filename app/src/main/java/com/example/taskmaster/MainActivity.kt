package com.example.taskmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.taskflow.nagivation.bottomNav.BottomNavBar
import com.example.taskmaster.navigation.NavHost.AppNavHost
import com.example.taskmaster.ui.theme.TaskMasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        // Optional: keep splash until your app is ready
        splashScreen.setKeepOnScreenCondition {
            false // or any condition you want
        }

        // Apply custom exit animation
        setSplashExitAnimation(splashScreen)

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

    private fun setSplashExitAnimation(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            val splashIcon = splashScreenViewProvider.iconView

            // 🔥 Customize your animation here
            splashIcon.animate()
                .setDuration(500L)
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .withEndAction {
                    splashScreenViewProvider.remove()
                }.start()
        }
    }
}