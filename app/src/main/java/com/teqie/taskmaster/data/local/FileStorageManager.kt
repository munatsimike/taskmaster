package com.teqie.taskmaster.data.local

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import com.teqie.taskmaster.domain.util.FileExtension
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FileStorageManager @Inject constructor(@ApplicationContext private val context: Context) {

    // Saves the file to the appropriate storage location with progress feedback
    suspend fun saveFileToStorage(
        body: ResponseBody,
        fileName: String,
        fileType: FileExtension,
        onProgress: (progress: Int) -> Unit // Progress callback
    ) {
        try {
            withContext(Dispatchers.IO) {
                saveFileToDownloads(body, fileName, fileType, onProgress)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Save file to public Downloads folder using MediaStore (Android Q+)
    private fun saveFileToDownloads(
        body: ResponseBody,
        fileName: String,
        fileType: FileExtension,
        onProgress: (progress: Int) -> Unit
    ) {
        val resolver = context.contentResolver

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.${fileType.extension}")
            put(MediaStore.MediaColumns.MIME_TYPE, getMimeType(fileType))
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            resolver.openOutputStream(uri).use { outputStream ->
                if (outputStream != null) {
                    body.byteStream().use { inputStream ->
                        val totalBytes = body.contentLength()
                        var bytesRead: Int
                        var totalBytesRead: Long = 0
                        val buffer = ByteArray(4096)

                        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                            outputStream.write(buffer, 0, bytesRead)
                            totalBytesRead += bytesRead
                            // Calculate progress and invoke callback
                            val progress = (totalBytesRead * 100 / totalBytes).toInt()
                            onProgress(progress)
                        }
                    }
                } else {
                    throw Exception("Failed to open OutputStream for URI")
                }
            }
        } else {
            throw Exception("Failed to create file in MediaStore")
        }
    }

    // Save file to public Downloads folder for Android versions below Q
    private fun saveFileToLegacyDownloads(
        body: ResponseBody,
        fileName: String,
        fileType: FileExtension,
        onProgress: (progress: Int) -> Unit
    ) {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }

        val file = File(downloadsDir, "$fileName.${fileType.extension}")

        body.byteStream().use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                val buffer = ByteArray(4096)
                var totalBytesRead = 0L
                val totalBytes = body.contentLength()
                var bytesRead: Int

                // Read and write the file in chunks
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead
                    // Calculate progress and invoke callback
                    val progress = (totalBytesRead * 100 / totalBytes).toInt()
                    onProgress(progress)
                }
                outputStream.flush()
            }
        }
    }

    // Utility function to get the correct MIME type based on file extension
    private fun getMimeType(fileType: FileExtension): String {
        return when (fileType.extension) {
            FileExtension.PDF.extension -> "application/pdf"
            FileExtension.DOCX.extension -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            // Add other file types as needed
            else -> "application/octet-stream"
        }
    }
}