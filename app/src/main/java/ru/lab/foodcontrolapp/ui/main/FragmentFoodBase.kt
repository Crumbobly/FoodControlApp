package ru.lab.foodcontrolapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.card.MaterialCardView
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.data.database.entity.Food
import ru.lab.foodcontrolapp.databinding.FragmentMainFoodBaseBinding
import ru.lab.foodcontrolapp.databinding.FragmentMainSettingsBinding
import ru.lab.foodcontrolapp.ui.customview.userdatainput.UserDataInput
import ru.lab.foodcontrolapp.utils.dpToPx
import ru.lab.foodcontrolapp.viewmodel.FoodBaseViewModel
import ru.lab.foodcontrolapp.viewmodel.SettingsViewModel
import ru.lab.foodcontrolapp.viewmodel.UserDataInputViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.FakeDBFoodViewModel
import ru.lab.foodcontrolapp.viewmodel.factory.FoodBaseViewModelFactory
import ru.lab.foodcontrolapp.viewmodel.factory.SettingsViewModelFactory

class FragmentFoodBase: Fragment(R.layout.fragment_main_food_base) {

    private lateinit var binding: FragmentMainFoodBaseBinding

    private val _fakeDBFoodViewModel: FakeDBFoodViewModel by viewModels()
    private val foodBaseViewModel: FoodBaseViewModel by viewModels {
        FoodBaseViewModelFactory(
            _fakeDBFoodViewModel
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_food_base,
            container,
            false
        )

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBtn.setOnClickListener {
            searchBtnClick()
        }
        searchBtnClick()

        foodBaseViewModel.fakeDBFoodViewModel.searchResults.observe(viewLifecycleOwner){
            list -> Log.d("FoodBase", "search result: $list")
            showLoading(false)
            updateSearchResults(list)
        }
    }

    private fun searchBtnClick(){
        clearSearchResults()
        showLoading(true)
        foodBaseViewModel.searchBtnPressed(binding.searchTextfield.text.toString())
    }

    private fun clearSearchResults(){
        for (i in binding.searchResultLayout.childCount - 1 downTo 0) {
            val child = binding.searchResultLayout.getChildAt(i)
            if (child.id != binding.progressBar.id){
                binding.searchResultLayout.removeViewAt(i)
            }
        }
    }

    private fun updateSearchResults(foodList: List<Food>) {
        clearSearchResults() // Очистка старых результатов
        binding.searchResultLayout.setPadding(0, 0, 16, 0)

        for (food in foodList) {

            val layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, if (foodList.indexOf(food) == 0) 16 else 0, 16, 16)
            }

            val cardView = ConstraintLayout(ContextThemeWrapper(requireContext(), R.style.ConstraintCardView))
            cardView.layoutParams = layoutParams


            val textView = TextView(requireContext()).apply {
                text = food.name
                textSize = 18f
                setPadding(16, 8, 16, 8)
            }

            cardView.addView(textView)
            cardView.setOnClickListener {
                //openFoodDetail(food)
            }

            binding.searchResultLayout.addView(cardView)
        }
    }

//    private fun openFoodDetail(food: Food) {
//        val fragment = FragmentFoodDetail()
//        val bundle = Bundle().apply {
//            putParcelable("food", food)
//        }
//        fragment.arguments = bundle
//
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.container, fragment)
//            .addToBackStack(null)
//            .commit()
//    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}