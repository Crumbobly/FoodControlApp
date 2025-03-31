package ru.lab.foodcontrolapp.viewmodel

import android.app.Application
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel

class WelcomeViewModel(
    private val application: Application,
    val userDataLocal: LiveData<User>,
    val userDataLocalIsFullyInput: LiveData<Boolean>,
    val dbUserViewModel: DBUserViewModel

): ViewModel() {

    private val _onChangeToMain = MutableLiveData<Unit>()
    val onChangeToMain: LiveData<Unit> get() = _onChangeToMain


    fun onBtnCancelPressed(){
        setFirstLaunchPreference()
        _onChangeToMain.postValue(Unit)
    }

    fun onBtnNextPressed(){
        setFirstLaunchPreference()
        saveUserInDB()
        _onChangeToMain.postValue(Unit)
    }

    private fun setFirstLaunchPreference(){
        val sharedPreferences = application.getSharedPreferences("user_prefs", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
    }

    private fun saveUserInDB(){
        userDataLocal.value?.let { dbUserViewModel.updateUserInDatabase(it, calculateDefaultCalories=true) }
    }

}