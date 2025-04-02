package ru.lab.foodcontrolapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.FakeDBFoodViewModel

class FoodBaseViewModel(
    val fakeDBFoodViewModel: FakeDBFoodViewModel
): ViewModel() {

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText

    var hasSearched = false

    fun searchTextChanged(text: String){
        _searchText.value = text
    }

    fun searchBtnPressed(){
        val toSearch = _searchText.value ?: ""
        fakeDBFoodViewModel.search(toSearch)
    }

    fun resetBtnPressed() {
        _searchText.postValue("")
        fakeDBFoodViewModel.search("")
    }

}

