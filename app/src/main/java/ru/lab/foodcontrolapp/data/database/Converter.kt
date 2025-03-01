package ru.lab.foodcontrolapp.data.database

import androidx.room.TypeConverter
import ru.lab.foodcontrolapp.data.database.entity.Gender

class Converter {

    @TypeConverter
    fun fromGender(value: Gender?): String? {
        return value?.name
    }

    @TypeConverter
    fun toGender(value: String?): Gender? {
        return value?.let { Gender.valueOf(it) }
    }

}