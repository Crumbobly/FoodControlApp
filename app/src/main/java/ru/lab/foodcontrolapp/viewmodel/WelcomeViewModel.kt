package ru.lab.foodcontrolapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lab.foodcontrolapp.data.database.AppDatabase
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.data.database.repositiry.UserRepository

class WelcomeViewModel(private val application: Application): AndroidViewModel(application) {

    private val _onChangeToMain = MutableLiveData<Boolean>()
    val onChangeToMain: LiveData<Boolean> get() = _onChangeToMain

    private val _onUserToSave = MutableLiveData<Boolean>()
    val onUserToSave: LiveData<Boolean> get() = _onUserToSave


    fun onBtnCancelPressed(){
        setFirstLaunchPreference()
        _onChangeToMain.postValue(true)
    }

    fun onBtnNextPressed(){
        setFirstLaunchPreference()
        _onUserToSave.postValue(true)
        _onChangeToMain.postValue(true)
    }

    private fun setFirstLaunchPreference(){
        val sharedPreferences = application.getSharedPreferences("user_prefs", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
    }

}