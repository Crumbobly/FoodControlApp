package ru.lab.foodcontrolapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.lab.foodcontrolapp.viewmodel.AddInMealViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBMealViewModel


class AddInMealViewModelFactory(
    private val dbMealViewModel: DBMealViewModel,
    private val edit: Boolean,
    private val id: Int,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddInMealViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddInMealViewModel(
                dbMealViewModel,
                edit,
                id
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}