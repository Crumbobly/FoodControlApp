package ru.lab.foodcontrolapp.viewmodel.appcontext


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import ru.lab.foodcontrolapp.data.database.FakeFoodDatabase
import ru.lab.foodcontrolapp.data.database.entity.Food

class FakeDBFoodViewModel(application: Application) : AndroidViewModel(application) {
    private val _searchResults = MutableLiveData<List<Food>>()
    val searchResults: LiveData<List<Food>> get() = _searchResults

    init {
        FakeFoodDatabase.loadFoodsFromXml(application.applicationContext)
    }

    fun search(query: String) {
        viewModelScope.launch {
            _searchResults.value = FakeFoodDatabase.searchFood(query)
        }
    }
}
