package com.teqie.taskmaster.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.file.MyFile
import com.teqie.taskmaster.ui.components.state.ProcessNetworkState
import com.teqie.taskmaster.ui.screen.FileItem

@Composable
fun <T: MyFile> DisplayFiles(
    checkedIds: List<String>,
    networkState: Resource<List<T>>,
    onCheckedChange: (String) -> Unit,
    onDeleteClick: (T) -> Unit,
    onEdit: (T) -> Unit
) {
    ProcessNetworkState(state = networkState) { files: List<T> ->
        LazyColumn(
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(files) { _, file ->
                FileItem(
                    file = file,
                    isCheck = checkedIds.contains(file.id),
                    onCheckedChange = { onCheckedChange(file.id) },
                    onDeleteClick = { onDeleteClick(file) },
                    onEditClick = { onEdit(file) }
                )
            }
        }
    }
}