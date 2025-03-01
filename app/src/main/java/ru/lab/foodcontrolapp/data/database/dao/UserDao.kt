package ru.lab.foodcontrolapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.lab.foodcontrolapp.data.database.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getUser(): User?

    @Update
    suspend fun updateUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

}