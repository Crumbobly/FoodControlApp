package ru.lab.foodcontrolapp.viewmodel.factory

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.viewmodel.SettingsViewModel
import ru.lab.foodcontrolapp.viewmodel.WelcomeViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel


class WelcomeViewModelFactory(
    private val application: Application,
    private val userDataLocal: LiveData<User>,
    private val userDataLocalIsFullyInput: LiveData<Boolean>,
    private val dbUserViewModel: DBUserViewModel,

    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WelcomeViewModel(
                application,
                userDataLocal,
                userDataLocalIsFullyInput,
                dbUserViewModel
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}