package ru.lab.foodcontrolapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lab.foodcontrolapp.data.database.entity.Gender
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel


class UserDataInputViewModel(): ViewModel() {

    private val _userData = MutableLiveData(User())
    val userData: LiveData<User> get() = _userData

    private val _userFullyInput = MutableLiveData(false)
    val userFullyInput: LiveData<Boolean> get() = _userFullyInput

    val dataAges: List<Int> = (1..100).toList()
    val dataHeight: List<Int> = (55..251).toList()
    val dataWeight: List<Int> = (1..250).toList()


    private fun notifyUserFullyInput()
    {
        if (userData.value?.gender != null &&
            userData.value?.age != null &&
            userData.value?.weight != null &&
            userData.value?.height != null)
        {
            _userFullyInput.value = true
        }

    }

    fun setLocalUser(user: User?){
        Log.d("UserDataIVM Set user", "$user")
        _userData.postValue(user ?: User())
    }

    fun updateLocalUser(age: Int? = null, height: Int? = null, weight: Int? = null, gender: Gender? = null) {
        val currentUser = _userData.value ?: return
        val updatedUser = currentUser.copy(
            age = age ?: currentUser.age,
            height = height ?: currentUser.height,
            weight = weight ?: currentUser.weight,
            gender = gender ?: currentUser.gender
        )
        _userData.value = updatedUser
        notifyUserFullyInput()
        Log.d("UserDataIVM Update user", "$updatedUser")
    }


}