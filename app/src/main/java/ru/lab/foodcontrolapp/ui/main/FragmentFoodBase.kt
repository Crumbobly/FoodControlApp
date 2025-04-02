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
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.data.database.entity.Food
import ru.lab.foodcontrolapp.databinding.FragmentMainFoodBaseBinding
import ru.lab.foodcontrolapp.databinding.FragmentMainSettingsBinding
import ru.lab.foodcontrolapp.ui.customview.userdatainput.UserDataInput
import ru.lab.foodcontrolapp.utils.dpToPx
import ru.lab.foodcontrolapp.utils.observeOnce
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

        binding.resetBtn.setOnClickListener {
            resetBtnClick()
        }

        binding.searchTextfield.addTextChangedListener { text ->
            foodBaseViewModel.searchTextChanged(text.toString())
        }

        if (!foodBaseViewModel.hasSearched) {
            searchBtnClick()
            foodBaseViewModel.hasSearched = true
        }

        foodBaseViewModel.searchText.observe(viewLifecycleOwner) { text ->
            val currentText = binding.searchTextfield.text.toString()

            if (currentText != text) {
                binding.searchTextfield.setText(text)
                binding.searchTextfield.setSelection(text.length)
            }
        }

        foodBaseViewModel.fakeDBFoodViewModel.searchResults.observe(viewLifecycleOwner){
            list -> Log.d("FoodBase", "search result: $list")
            showLoading(false)
            updateSearchResults(list)
        }
    }

    private fun resetBtnClick(){
        clearSearchResults()
        showLoading(true)
        foodBaseViewModel.resetBtnPressed()
    }

    private fun searchBtnClick(){
        clearSearchResults()
        showLoading(true)
        foodBaseViewModel.searchBtnPressed()
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
                setMargins(
                    dpToPx(requireContext(), 16),
                    if (foodList.indexOf(food) == 0) dpToPx(requireContext(), 16) else 0,
                    dpToPx(requireContext(), 16),
                    dpToPx(requireContext(), 16))
            }

            val cardView = ConstraintLayout(ContextThemeWrapper(requireContext(), R.style.ConstraintCardView))
            cardView.layoutParams = layoutParams

            val textView = TextView(requireContext()).apply {
                text = food.name
                textSize = 14f
                setPadding(0, 8, 0, 8)
            }

            cardView.addView(textView)
            cardView.setOnClickListener {
                openFoodDetail(food)
            }

            binding.searchResultLayout.addView(cardView)
        }
    }

    private fun openFoodDetail(food: Food) {
        val action = FragmentFoodBaseDirections.actionFragmentFoodBaseToFragmentFoodDetail(food)
        findNavController().navigate(action)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}