package ru.lab.foodcontrolapp.data.database.dao
import androidx.room.*
import ru.lab.foodcontrolapp.data.database.entity.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodEntry(entry: Meal)

    @Query("UPDATE meal_table SET weight = :newWeight WHERE id = :id")
    suspend fun updateMealWeight(newWeight: Int, id: Int)

    @Delete
    suspend fun deleteFoodEntry(entry: Meal)

    @Query("SELECT * FROM meal_table WHERE date = :date")
    suspend fun getFoodEntriesByDate(date: String): List<Meal>

}
