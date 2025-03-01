package ru.lab.foodcontrolapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lab.foodcontrolapp.data.database.entity.Gender
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.data.database.repositiry.UserRepository

class UserViewModel(
    private val repository: UserRepository,
    private val application: Application)
    : AndroidViewModel(application) {

    fun loadUser(callback: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUser()
            callback(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

}
