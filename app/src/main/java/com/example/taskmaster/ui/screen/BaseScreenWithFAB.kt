package com.example.taskmaster.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.R
import com.example.taskmaster.domain.LoggedInUser
import com.example.taskmaster.ui.common.AddFloatingActionButton
import com.example.taskmaster.ui.common.factory.AnimationFactory.AnimatedColor
import com.example.taskmaster.ui.common.header.HeaderData
import com.example.taskmaster.ui.common.imageDisplay.DisplayImageVectorIcon
import com.example.taskmaster.ui.common.imageDisplay.NetworkImageLoader
import com.example.taskmaster.ui.theme.darkBlue
import com.example.taskmaster.ui.theme.gradientEnd
import com.example.taskmaster.ui.theme.gradientStart
import com.example.taskmaster.ui.theme.lightGray

@Composable
fun BaseScreenWithFAB(
    headerData: HeaderData,
    isFabVisible: Boolean,
    fabBtnText: String,
    onLogoutClick: () -> Unit,
    onFabClick: () -> Unit,
    filterMenu: @Composable () -> Unit = {},
    isTitleVisible: Boolean = true,
    onBackButtonClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    AddFloatingActionButton(
        buttonText = fabBtnText,
        onClick = { onFabClick() },
        isFBVisible = isFabVisible
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(color = lightGray)
        ) {
            Column {
                DisplayScreenHeader(
                    headerData = headerData,
                    filterMenu = filterMenu,
                    isTitleVisible = isTitleVisible,
                    onButtonClick = onBackButtonClick
                )
                content()
            }
            DisplayLoggedInUserProfileOverlay(headerData.loggedInUser, onLogoutClick)
        }
    }
}

@Composable
fun DisplayScreenHeader(
    headerData: HeaderData,
    filterMenu: @Composable () -> Unit = {},
    isTitleVisible: Boolean = true,
    onButtonClick: (() -> Unit)? = null,
) {
    val cornerShape = 1.dp

    // Infinite gradient animation (optional
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                // Diagonal gradient with animation
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AnimatedColor().value,
                        AnimatedColor(initialValue = gradientEnd, targetValue = gradientStart).value
                    ),
                )
            )
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(bottomStart = cornerShape, bottomEnd = cornerShape)
            ) // Subtle shadow for depth
            .padding(10.dp)
            .heightIn(85.dp) // Adjust height if needed
            .animateContentSize(animationSpec = tween(durationMillis = 500))
    ) {
        // Username and Back Button Row
        DisplayUsernameBackButtonRow(
            headerData = headerData,
            filterMenu = filterMenu
        ) {
            onButtonClick?.invoke()
        }

        // Title Section
        AnimatedPageTitleSection(isTitleVisible = isTitleVisible, headerData)
    }
}

@Composable
fun AnimatedPageTitleSection(isTitleVisible: Boolean, headerData: HeaderData) {
    val slideDuration = 300
    AnimatedVisibility(
        visible = isTitleVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = slideDuration)) +
                slideInVertically(
                    initialOffsetY = { -it }, // Slide from top
                    animationSpec = tween(durationMillis = slideDuration)
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = slideDuration)) +
                slideOutVertically(
                    targetOffsetY = { -it }, // Slide to top
                    animationSpec = tween(durationMillis = slideDuration)
                )
    ) {
        ProjectTitleAndCurrentScreen(headerData)
    }
}

@Composable
fun DisplayUsernameBackButtonRow(
    headerData: HeaderData,
    filterMenu: @Composable () -> Unit,
    onButtonClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        DisplayBackButton(headerData.showBackButton, onButtonClick)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            filterMenu()
        }
    }
}

@Composable
fun DisplayBackButton(
    showBackButton: Boolean,
    onButtonClick: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier
) {
    if (showBackButton) {
        //display back button
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .then(modifier)
                .border(
                    width = 1.dp,
                    color = Color.Unspecified,
                    shape = CircleShape
                )
                .padding(4.dp)
                .clickable { onButtonClick() }
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = tint,
                modifier = Modifier.size(30.dp),
                contentDescription = null
            )
        }
    } else {
        Spacer(modifier = Modifier.fillMaxWidth(0.5f))
    }
}

@Composable
fun DisplayLoggedInUserProfileOverlay(
    loggedInUser: LoggedInUser,
    onLogoutClick: () -> Unit
) {
    var showProfile by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(), horizontalAlignment = Alignment.End
    ) {
        NetworkImageLoader(
            imageUrl = loggedInUser.avatar,
            modifier = Modifier.padding(top = 10.dp, end = 10.dp)
                .size(30.dp)
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
                                .size(50.dp)
                                .clip(CircleShape)
                        )

                        Column {
                            Text(text = loggedInUser.name)
                            Text(text = loggedInUser.email)
                            Text(text = loggedInUser.phone)
                        }
                    }
                }

                Box(modifier = Modifier.fillMaxWidth().background(darkBlue)) {
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

@Composable
private fun ProjectTitleAndCurrentScreen(headerData: HeaderData, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.house_24px),
            contentDescription = "",
            modifier.size(30.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
        if (headerData.projectTitle.isNotBlank())
            Text(text = headerData.projectTitle, color = MaterialTheme.colorScheme.onPrimary)

        Text(
            text = headerData.currentPage,
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}