package com.example.taskmaster.ui.common.factory

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.taskmaster.R
import com.example.taskmaster.ui.common.imageDisplay.DisplayImageVectorIcon
import com.example.taskmaster.ui.common.ErrorContent
import com.example.taskmaster.ui.common.factory.TextFactory.LabelText


object TextFieldFactory {

    @Composable
    fun FilledTextField(
        value: String,
        label: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        isSingleLine: Boolean = false,
        leadingIcon: ImageVector? = null,
        isPassword: Boolean = false,
        isPasswordVisible: Boolean = false,
        onPasswordToggle: (() -> Unit)? = null,
        visualTransformation : VisualTransformation= if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        errorMessage: String? = null
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = isSingleLine,
                label = { LabelText(text = label) },
                leadingIcon = leadingIcon?.let {
                    { Icon(imageVector = it, contentDescription = null) }
                },
                trailingIcon = if (isPassword && onPasswordToggle != null) {
                    {
                        Crossfade(targetState = isPasswordVisible, label = "") { isVisible ->
                            val icon = if (isVisible) R.drawable.visibility_off_24px else R.drawable.visibility_24px
                            DisplayImageVectorIcon(
                                icon = ImageVector.vectorResource(id = icon),
                                modifier = Modifier.clickable { onPasswordToggle() },
                                iconSize = 27,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                } else null,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),

                visualTransformation = visualTransformation,
                modifier = modifier.then(Modifier.fillMaxWidth())
            )

            if (errorMessage != null) {
                ErrorContent(errorMessage)
            }
        }
    }
}
