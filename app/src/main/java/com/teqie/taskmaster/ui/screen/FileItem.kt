package com.teqie.taskmaster.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.model.file.MyFile
import com.teqie.taskmaster.domain.util.getFileExtension
import com.teqie.taskmaster.domain.util.toFileExtension
import com.teqie.taskmaster.ui.components.AnimatedLinearProgressBar
import com.teqie.taskmaster.ui.components.CustomCheckBox
import com.teqie.taskmaster.ui.components.DocumentPreviewer
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.SecondaryButton
import com.teqie.taskmaster.ui.components.factory.TextFactory.BodyText
import com.teqie.taskmaster.ui.components.menu.DeleteEditOptionsMenu
import com.teqie.taskmaster.ui.viewModel.file.FileManagementViewModel

@Composable
fun FileItem(
    file: MyFile?,
    isCheck: Boolean,
    onCheckedChange: (String) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    fileManagementViewModel: FileManagementViewModel = hiltViewModel()
) {
    // State to track download progress (0.0 to 1.0)
    var progress by remember { mutableFloatStateOf(0f) }
    var isDownloading by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth())
    {
        if (file != null) {
            // Your custom composable that displays file info
            Box(contentAlignment = Alignment.TopCenter) {
                FileContent(
                    fileName = file.fileName,
                    fileUri = file.url,
                    isChecked = isCheck,
                    onCheckedChange = { onCheckedChange(file.id) },
                    onEditClick = { onEditClick() },
                    onDeleteClick = { onDeleteClick() },
                    onDownloadClick = {
                        val url = file.url
                        val fileName = file.fileName
                        val fileType = file.url.getFileExtension()

                        // Start the download and manage the progress state
                        if (fileType != null) {
                            isDownloading = true
                            fileType.toFileExtension()?.let {
                                fileManagementViewModel.downloadFile(
                                    url,
                                    fileName,
                                    it
                                ) { progressValue: Int ->
                                    // Update progress as a float between 0.0 and 1.0
                                    progress = progressValue / 100f

                                    // Stop downloading when complete
                                    if (progressValue == 100) {
                                        isDownloading = false
                                    }
                                }
                            }
                        }
                    }
                )
                // Show progress bar when downloading
                if (isDownloading) {
                    AnimatedLinearProgressBar(progress = progress)
                }
            }
        }
    }
}

@Composable
private fun FileContent(
    fileName: String,
    fileUri: String,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    onEditClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
            ) {
                CustomCheckBox(
                    isChecked = isChecked,
                    onCheckedChange = { onCheckedChange() },
                    content = {
                        DocumentPreviewer(fileUri = fileUri)
                        Spacer(modifier = Modifier.size(9.dp))
                        BodyText(text = fileName)
                    })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                SecondaryButton(
                    text = stringResource(R.string.download),
                    onClick = { onDownloadClick() },
                    icon = R.drawable.download_24px,
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider()
        }

        DeleteEditOptionsMenu(
            item = fileName,
            onEditClick = { onEditClick() },
            onDeleteClick = { onDeleteClick() },
            canDelete = true,
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}