package com.teqie.taskmaster.ui.screen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.components.AddFloatingActionButton
import com.teqie.taskmaster.ui.components.factory.AnimationFactory.AnimatedColor
import com.teqie.taskmaster.ui.components.header.HeaderData
import com.teqie.taskmaster.ui.theme.gradientEnd
import com.teqie.taskmaster.ui.theme.gradientStart
import com.teqie.taskmaster.ui.theme.lightGray
import com.teqie.taskmaster.util.components.DisplayLoggedInUserProfileOverlay

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
    tint: Color = MaterialTheme.colorScheme.onSurface,
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
                imageVector = ImageVector.vectorResource(R.drawable.arrow_back_ios_new_24px),
                tint = tint,
                modifier = Modifier.size(25.dp),
                contentDescription = null
            )
        }
    } else {
        Spacer(modifier = Modifier.fillMaxWidth(0.5f))
    }
}



@Composable
private fun ProjectTitleAndCurrentScreen(headerData: HeaderData, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        /**Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.house_24px),
            contentDescription = "",
            modifier.size(30.dp),
            tint = orange
        )**/

        Text(
            text = headerData.currentPage,
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (headerData.projectTitle.isNotBlank())
            Text(text = headerData.projectTitle, color = MaterialTheme.colorScheme.onSurface)
    }
}