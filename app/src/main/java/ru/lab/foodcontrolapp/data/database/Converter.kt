package ru.lab.foodcontrolapp.data.database

import androidx.room.TypeConverter
import ru.lab.foodcontrolapp.data.database.entity.Food
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

    @TypeConverter
    fun fromFood(food: Food): String {
        return "${food.name};${food.type};${food.group};${food.calories};${food.fat};${food.protein};${food.carbs}"
    }

    @TypeConverter
    fun toFood(data: String): Food {
        val parts = data.split(";")
        return Food(
            name = parts[0],
            type = parts[1],
            group = parts[2],
            calories = parts[3].toInt(),
            fat = parts[4].toFloat(),
            protein = parts[5].toFloat(),
            carbs = parts[6].toFloat()
        )
    }

}