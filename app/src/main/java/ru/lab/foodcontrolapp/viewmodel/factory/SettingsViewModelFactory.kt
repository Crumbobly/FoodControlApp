package ru.lab.foodcontrolapp.viewmodel.factory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.viewmodel.SettingsViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel

class SettingsViewModelFactory(
    private val userDataLocal: LiveData<User>,
    private val userDataGlobal: LiveData<User?>,
    private val isUserDataComplete: LiveData<Boolean>,
    private val dbUserViewModel: DBUserViewModel,

    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                userDataLocal,
                userDataGlobal,
                isUserDataComplete,
                dbUserViewModel
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
