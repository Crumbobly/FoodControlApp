package ru.lab.foodcontrolapp.viewmodel.factory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.viewmodel.FoodBaseViewModel
import ru.lab.foodcontrolapp.viewmodel.SettingsViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.FakeDBFoodViewModel

class FoodBaseViewModelFactory(

    private val _fakeDBFoodViewModel: FakeDBFoodViewModel

    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodBaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodBaseViewModel(
                _fakeDBFoodViewModel
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
