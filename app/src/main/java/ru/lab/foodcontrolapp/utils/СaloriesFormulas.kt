package ru.lab.foodcontrolapp.utils

import ru.lab.foodcontrolapp.data.database.entity.Gender
import ru.lab.foodcontrolapp.data.database.entity.User

// Миффлин-Сан Жеор
fun calculateDailyCalories(user: User): Int {

    if (user.height == null || user.age == null || user.weight == null || user.gender == null){
        return 0
    }

    val bmrMale = 88.36 + (13.4 * user.weight) + (4.8 * user.height) - (5.7 * user.age) // Мужчины
    val bmrFemale = 447.6 + (9.2 * user.weight) + (3.1 * user.height) - (4.3 * user.age) // Женщины
    val bmrOther = (bmrMale + bmrFemale) / 2

    return when (user.gender){
        Gender.MALE -> bmrMale.toInt()
        Gender.FEMALE -> bmrFemale.toInt()
        Gender.OTHER -> bmrOther.toInt()
    }
}