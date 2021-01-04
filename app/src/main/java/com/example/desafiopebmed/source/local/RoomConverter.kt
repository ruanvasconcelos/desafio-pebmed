package com.example.desafiopebmed.source.local

import androidx.room.TypeConverter
import com.example.desafiopebmed.repository.vo.ComponentType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class RoomConverter {

    @TypeConverter
    fun componentTypeToString(componentType: ComponentType?) = componentType?.name

    @TypeConverter
    fun stringToComponentType(string: String?) = string?.let(ComponentType::valueOf)

    @TypeConverter
    fun stringToAuthorList(value: String?): List<AuthorEntity>? {
        if (value == null) {
            return Collections.emptyList()
        }

        val listType: Type = object : TypeToken<List<AuthorEntity>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun authorListToString(list: List<AuthorEntity>?): String? {
        val gson = Gson()
        return gson.toJson(list)

    }
}