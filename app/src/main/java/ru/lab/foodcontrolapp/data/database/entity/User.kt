package ru.lab.foodcontrolapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "weight")
    val weight: Int? = null,

    @ColumnInfo(name = "height")
    val height: Int? = null,

    @ColumnInfo(name = "age")
    val age: Int? = null,

    @ColumnInfo(name = "gender")
    val gender: Gender? = null
)
