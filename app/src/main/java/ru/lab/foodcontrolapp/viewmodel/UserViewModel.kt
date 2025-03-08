package ru.lab.foodcontrolapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lab.foodcontrolapp.data.database.AppDatabase
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.data.database.repositiry.UserRepository

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val repository  = UserRepository(AppDatabase.getInstance(application).getUserDao())

    private val _userData = MutableLiveData<User?>()
    val userData: MutableLiveData<User?> get() = _userData

    init {
        Log.d("UserGlobalViewModel", "View model init")
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val user = repository.getUser()
            Log.d("UserGlobalViewModel", "Load user from database: $user")
            _userData.postValue(user ?: User())
        }
    }

    fun insertUserToDatabase(userToSave: User) {
        userToSave.let { user ->
            _userData.postValue(user)
            Log.d("UserGlobalViewModel", "Save user in database: $user")
            viewModelScope.launch {
                repository.insertUser(user)
            }
        }
    }

}