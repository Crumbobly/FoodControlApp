package ru.lab.foodcontrolapp.viewmodel

import android.app.Application
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lab.foodcontrolapp.data.database.AppDatabase
import ru.lab.foodcontrolapp.data.database.entity.Meal
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel
import java.util.Locale

class HomeViewModel(
    application: Application,
    val dbUserViewModel: DBUserViewModel
) : AndroidViewModel(application) {

    private val mealDao = AppDatabase.getInstance(application).getMealDao()

    private val _mealsForToday = MutableLiveData<List<Meal>>()
    val mealsForToday: LiveData<List<Meal>> = _mealsForToday

    private val _navigateToFoodBase = MutableLiveData<Boolean>()
    val navigateToFoodBase: LiveData<Boolean> = _navigateToFoodBase

    private val _kcalSummary = MutableLiveData<Int>()
    val kcalSummary: LiveData<Int> = _kcalSummary

    fun onAddNewMealBtnClicked() {
        _navigateToFoodBase.value = true
    }

    fun onNavigationDone() {
        _navigateToFoodBase.value = false
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    fun loadMealsForToday() {
        val todayDate = getCurrentDate()
        viewModelScope.launch {
            val meals = mealDao.getFoodEntriesByDate(todayDate)
            _kcalSummary.value = getKcalSummary(meals)
            dbUserViewModel.userData.value?.calories
            _mealsForToday.postValue(meals)
        }
    }


    private fun getKcalSummary(meals: List<Meal>): Int {
        var sum = 0
        for (item in meals){
            sum += item.weight/100 * item.food.calories
        }
        return sum
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDao.deleteFoodEntry(meal)
            loadMealsForToday()
        }
    }

}