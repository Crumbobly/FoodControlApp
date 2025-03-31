package ru.lab.foodcontrolapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.FakeDBFoodViewModel

class FoodBaseViewModel(
    val fakeDBFoodViewModel: FakeDBFoodViewModel
): ViewModel() {

    fun searchBtnPressed(text: String){
        fakeDBFoodViewModel.search(text)
        Log.d("FoodBase Search", text)
    }

}

