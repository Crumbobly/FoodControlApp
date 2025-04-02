package ru.lab.foodcontrolapp.viewmodel.appcontext

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch
import ru.lab.foodcontrolapp.data.database.AppDatabase
import ru.lab.foodcontrolapp.data.database.dao.MealDao
import ru.lab.foodcontrolapp.data.database.entity.Food
import ru.lab.foodcontrolapp.data.database.entity.Meal

class DBMealViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: MealDao = AppDatabase.getInstance(application).getMealDao()

    fun addMeal(date: String, food: Food, weight: Int) {
        viewModelScope.launch {
            dao.insertFoodEntry(Meal(date = date, food = food, weight = weight))
        }
    }

    fun updateWeightMeal(newWeight: Int, id: Int) {
        viewModelScope.launch {
            dao.updateMealWeight(newWeight, id)
        }
    }

    fun getMealsForDate(date: String): LiveData<List<Meal>> {
        val liveData = MutableLiveData<List<Meal>>()
        viewModelScope.launch {
            liveData.postValue(dao.getFoodEntriesByDate(date))
        }
        return liveData
    }
}
