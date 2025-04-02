package ru.lab.foodcontrolapp.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.lab.foodcontrolapp.viewmodel.FoodBaseViewModel
import ru.lab.foodcontrolapp.viewmodel.HomeViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.FakeDBFoodViewModel

class HomeViewModelFactory(
    private val application: Application,
    private val _dbUserViewModel: DBUserViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(
                application,
                _dbUserViewModel
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
