package com.teqie.taskmaster.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.components.factory.AnimationFactory
import com.teqie.taskmaster.ui.components.imageDisplay.DisplayImageVectorIcon
import com.teqie.taskmaster.ui.theme.orange

object SplashScreen {

    @Composable
    fun MainScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                // App Logo or Icon
                DisplayImageVectorIcon(
                    icon = ImageVector.vectorResource(R.drawable.home_logo),
                    iconSize = 96,
                    tint = orange
                )
                // App Name or Branding
                Text(
                    text = stringResource(id = R.string.brass_ring_collective),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                // Tagline
                Text(
                    text = stringResource(id = R.string.Managing_real_estate_eamlessly),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )

                // Subtle loading animation
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) { index ->
                        val delayMillis = index * 150
                        val alpha = AnimationFactory.rememberFadeInOutAnimation(
                            initialValue = 0.3f,
                            targetValue = 1f,
                            durationMillis = 600 + delayMillis // offset using delay
                        )
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .padding(4.dp)
                                .background(
                                    Color(0xFF1B5E20).copy(alpha = alpha.value),
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

