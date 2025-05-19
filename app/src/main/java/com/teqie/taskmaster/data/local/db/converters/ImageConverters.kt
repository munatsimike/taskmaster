package com.teqie.taskmaster.data.local.db.converters

import androidx.room.TypeConverter
import com.teqie.taskmaster.data.local.db.enties.FolderEntity
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ImageConverters {

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {  ignoreUnknownKeys = true
        explicitNulls = false
        isLenient = true}

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return json.encodeToString(list)
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return json.decodeFromString(data)
    }
}