package com.teqie.taskmaster.util.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.LoggedInUser
import com.teqie.taskmaster.ui.components.imageDisplay.DisplayImageVectorIcon
import com.teqie.taskmaster.ui.components.imageDisplay.NetworkImageLoader
import com.teqie.taskmaster.ui.theme.darkBlue

@Composable
fun DisplayLoggedInUserProfileOverlay(
    loggedInUser: LoggedInUser,
    onLogoutClick: () -> Unit
) {
    var showProfile by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth().clickable { showProfile = !showProfile }, Alignment.TopEnd
    ) {
        NetworkImageLoader(
            imageUrl = loggedInUser.avatar,
            modifier = Modifier.padding(top = 10.dp, end = 10.dp)
                .size(50.dp)
                .clip(CircleShape)
                .clickable {
                    showProfile = !showProfile
                }
        )
        LoggedInUserProfile(loggedInUser, showProfile = showProfile, onLogoutClick = onLogoutClick)
    }
}

@Composable
private fun LoggedInUserProfile(
    loggedInUser: LoggedInUser,
    showProfile: Boolean,
    onLogoutClick: () -> Unit
) {
    AnimatedVisibility(visible = showProfile) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                ),

            shadowElevation = 5.dp
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, bottom = 5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        NetworkImageLoader(
                            imageUrl = loggedInUser.avatar,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Column {
                            Text(text = loggedInUser.name)
                            Text(text = loggedInUser.email)
                            Text(text = loggedInUser.phone)
                        }
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth().background(darkBlue)
                        .clickable { onLogoutClick() }) {
                    TextButton(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = { onLogoutClick() }
                    ) {
                        DisplayImageVectorIcon(
                            icon = ImageVector.vectorResource(R.drawable.logout_24px),
                            iconSize = 22,
                            tint = Color.White
                        )
                        Text(
                            text = "Sign out",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}