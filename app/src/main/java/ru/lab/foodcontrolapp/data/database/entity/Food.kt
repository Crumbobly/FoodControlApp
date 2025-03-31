package ru.lab.foodcontrolapp.data.database.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val name: String,
    val type: String,
    val group: String,
    val calories: Int,
    val fat: Float,
    val protein: Float,
    val carbs: Float
) : Parcelable
