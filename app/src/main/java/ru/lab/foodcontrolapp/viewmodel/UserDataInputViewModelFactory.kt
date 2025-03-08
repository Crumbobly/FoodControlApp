package ru.lab.foodcontrolapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.lab.foodcontrolapp.data.database.entity.User

class UserDataInputViewModelFactory(private val user: User?) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDataInputViewModel::class.java)) {
            return UserDataInputViewModel().apply {
                setLocalUser(user)
            } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
