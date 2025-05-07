package com.teqie.taskmaster.ui.components.form

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.components.ErrorContent

@Composable
fun FileSelector(
    actionText: String = stringResource(id = R.string.select_file),
    selector: @Composable ((String, () -> Unit) -> Unit)? = null,
    selectorColor: Color = MaterialTheme.colorScheme.tertiary,
    errorText: String,
    onFileSelected: (Uri?) -> Unit
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            onFileSelected(uri)
        }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (selector == null) {
            ActionBtn(
                onClick = { launcher.launch("*/*") },
                actionText = actionText,
                btnColor = selectorColor
            )
        } else {
            selector(actionText) { launcher.launch("*/*") }
        }

        if (errorText.isNotBlank()) {
            ErrorContent(message = errorText)
        }
    }
}

@Composable
private fun ActionBtn(onClick: () -> Unit, actionText: String, btnColor: Color) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = btnColor),
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.upload_file_24px),
                contentDescription = null
            )
            Text(text = actionText, fontSize = 16.sp)
        }
    }
}