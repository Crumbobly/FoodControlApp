package ru.lab.foodcontrolapp.viewmodel

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lab.foodcontrolapp.data.database.entity.Food
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBMealViewModel
import java.util.Locale

class AddInMealViewModel(
    private val dbMealViewModel: DBMealViewModel,
    private val edit: Boolean,
    private val id: Int
) : ViewModel() {

    private val _mealWeight = MutableLiveData<Int>()
    val mealWeight: LiveData<Int> = _mealWeight

    private val _navigateBack = MutableLiveData<Boolean>()
    val navigateBack: LiveData<Boolean> = _navigateBack

    private val _addMeal = MutableLiveData<Boolean>()
    val addMeal: LiveData<Boolean> = _addMeal

    fun onAddBtnClicked(food: Food) {
        _addMeal.value = true
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Calendar.getInstance().time)

        if (edit) {
            mealWeight.value?.let { dbMealViewModel.updateWeightMeal(it, id) }
        } else {
            mealWeight.value?.let { dbMealViewModel.addMeal(currentDate, food, it) }
        }

    }

    fun onBackBtnClicked() {
        _navigateBack.value = true  // Сообщаем, что надо вернуться назад
    }

    fun onWeightChanged(weightString: String) {
        _mealWeight.value = weightString.toIntOrNull() ?: 0
    }

    fun onNavigationDone() {
        _navigateBack.value = false
        _addMeal.value = false
    }
}
