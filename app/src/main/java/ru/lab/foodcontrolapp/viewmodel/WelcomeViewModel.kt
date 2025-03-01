package ru.lab.foodcontrolapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lab.foodcontrolapp.data.database.entity.Gender
import ru.lab.foodcontrolapp.data.database.entity.User

class WelcomeViewModel : ViewModel() {
    private var _userData: User = User()

    fun getUserData(): User {
        return _userData
    }

    fun setUserData(user: User) {
        _userData = user
    }
}