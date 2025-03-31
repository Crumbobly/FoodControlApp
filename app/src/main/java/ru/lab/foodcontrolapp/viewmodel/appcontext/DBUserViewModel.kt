package ru.lab.foodcontrolapp.viewmodel.appcontext

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lab.foodcontrolapp.data.database.AppDatabase
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.utils.calculateDailyCalories

class DBUserViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).getUserDao()

    private val _userData = MutableLiveData<User?>()
    val userData: LiveData<User?> get() = _userData

    private val _isUserDataComplete = MutableLiveData<Boolean>()
    val isUserDataComplete: LiveData<Boolean> get() = _isUserDataComplete

    init {
        Log.d("DBUserViewModel", "View model init")
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val user = dao.getUser()
            _userData.postValue(user)
            setUserDataComplete(user)
            Log.d("DBUserViewModel", "Load user from database: $user")
        }
    }

    fun updateUserInDatabase(userToSave: User, calculateDefaultCalories: Boolean = false) {

        var user = userToSave

        if (calculateDefaultCalories) {
            user = userToSave.copy(calories = calculateDailyCalories(user))
        }

        viewModelScope.launch {
            dao.updateUser(user)
            _userData.postValue(user)
            setUserDataComplete(user)
            Log.d("DBUserViewModel", "Update user in database: $user")
        }
    }

    private fun setUserDataComplete(user: User?) {
        if (user != null) {
            _isUserDataComplete.postValue(
                            user.age != null &&
                            user.weight != null &&
                            user.height != null &&
                            user.gender != null
                    )
        }
        else {
            _isUserDataComplete.postValue(false)
        }
    }
}
