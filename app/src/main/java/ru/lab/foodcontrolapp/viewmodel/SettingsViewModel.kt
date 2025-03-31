package ru.lab.foodcontrolapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel

class SettingsViewModel(

    val userDataLocal: LiveData<User>,
    val userDataGlobal: LiveData<User?>,
    val isUserDataComplete: LiveData<Boolean>,
    val dbUserViewModel: DBUserViewModel

) : ViewModel() {

    private val _resetUser = MutableLiveData<User>()
    val resetUser: LiveData<User> get() = _resetUser

    private val _resetCalories = MutableLiveData<Int>()
    val resetCalories: LiveData<Int> get() = _resetCalories

    fun onBtnEditUserPressed(){
        _resetUser.value = dbUserViewModel.userData.value
    }
    fun onBtnCancelUserPressed(){
        _resetUser.value = dbUserViewModel.userData.value
    }
    fun onBtnSaveUserPressed(){
        userDataLocal.value?.let { dbUserViewModel.updateUserInDatabase(it, calculateDefaultCalories=false) }
        _resetUser.value = userDataLocal.value
    }

    fun onBtnEditKCalPressed(){
        _resetCalories.value = dbUserViewModel.userData.value?.calories
    }
    fun onBtnCancelKCalPressed(){
        _resetCalories.value = dbUserViewModel.userData.value?.calories
    }
    fun onBtnSaveKCalPressed(){
        dbUserViewModel.updateUserInDatabase(dbUserViewModel.userData.value!!.copy(calories = _resetCalories.value!!))
    }

    fun onUpdateCaloriesBtnPressed(){
        dbUserViewModel.userData.value?.let {
            dbUserViewModel.updateUserInDatabase(it, calculateDefaultCalories=true)
        }
    }

    fun setLocalCalories(calories: Int){
        _resetCalories.value = calories
    }

    fun changeLocalCalories(increase: Boolean, index: Int) {
        val current = _resetCalories.value

        val digits = current.toString().padStart(4, '0').toCharArray()
        if (index in digits.indices) {
            var digit = digits[index].digitToInt()

            digit = if (increase) {
                (digit + 1) % 10 // Увеличиваем, не выходя за 9
            } else {
                (digit + 9) % 10 // Уменьшаем, не выходя за 0
            }

            digits[index] = digit.digitToChar()
            val newCalories = digits.concatToString().toInt()

            _resetCalories.value = newCalories
        }
    }


}