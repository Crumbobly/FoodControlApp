package ru.lab.foodcontrolapp.data.database.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_table")
data class Meal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, // Дата в формате "YYYY-MM-DD"
    val food: Food,
    val weight: Int
)
