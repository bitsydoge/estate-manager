package com.cold0.realestatemanager.repository.database

import androidx.room.TypeConverter
import com.cold0.realestatemanager.model.EstateType
import com.cold0.realestatemanager.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class EstateDatabaseConverter {
    var gson: Gson = Gson()

    @TypeConverter
    fun toEstateType(value: Int) = enumValues<EstateType>()[value]

    @TypeConverter
    fun fromEstateType(value: EstateType) = value.ordinal

    @TypeConverter
    fun toPhoto(data: String?): List<Photo> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Photo?>?>() {}.getType()
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromPhoto(someObjects: List<Photo?>?): String {
        return gson.toJson(someObjects)
    }
}