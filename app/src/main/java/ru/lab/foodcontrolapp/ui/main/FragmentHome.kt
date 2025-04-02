package ru.lab.foodcontrolapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.data.database.entity.Meal
import ru.lab.foodcontrolapp.databinding.FragmentMainFoodBaseBinding
import ru.lab.foodcontrolapp.databinding.FragmentMainHomeBinding
import ru.lab.foodcontrolapp.viewmodel.HomeViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.FakeDBFoodViewModel
import ru.lab.foodcontrolapp.viewmodel.factory.HomeViewModelFactory


class FragmentHome: Fragment(R.layout.fragment_main_home) {

    private lateinit var binding: FragmentMainHomeBinding
    private val _dbUserDataViewModel: DBUserViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels{
        HomeViewModelFactory(
            requireActivity().application,
            _dbUserDataViewModel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_home,
            container,
            false
        )

        return binding.root
    }

    private fun clearTodayMeals(){
        for (i in binding.todayMeals.childCount - 1 downTo 0) {
           binding.todayMeals.removeViewAt(i)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addNewMealBtn.setOnClickListener {
            homeViewModel.onAddNewMealBtnClicked()
        }

        homeViewModel.navigateToFoodBase.observe(viewLifecycleOwner) { state ->
            if (state) {
                requireActivity().findViewById<BottomNavigationView>(R.id.main_bottom_menu).selectedItemId =
                    R.id.fragmentFoodBase

                homeViewModel.onNavigationDone()
            }
        }
        homeViewModel.loadMealsForToday()

        homeViewModel.mealsForToday.observe(viewLifecycleOwner) { meals ->
            updateMeals(meals)
        }
        homeViewModel.kcalSummary.observe(viewLifecycleOwner){ kcal ->
            binding.currentCaloriesLbl.text = "$kcal"
        }
        homeViewModel.dbUserViewModel.userData.observe(viewLifecycleOwner){ user ->
            if (user != null) {
                binding.maxCaloriesLbl.text = "${user.calories}"
            }
        }

    }

    private fun updateMeals(meals: List<Meal>) {
        clearTodayMeals()
        for (meal in meals) {
            val mealView =
                LayoutInflater.from(context).inflate(R.layout.cw_item_meal, binding.todayMeals, false)
            val mealName = mealView.findViewById<TextView>(R.id.mealName)
            val mealWeight = mealView.findViewById<TextView>(R.id.mealWeight)
            val deleteBtn = mealView.findViewById<Button>(R.id.deleteBtn)
            val editBtn = mealView.findViewById<Button>(R.id.editBtn)

            mealName.text = meal.food.name
            mealWeight.text = "${meal.weight}"

            deleteBtn.setOnClickListener {
                homeViewModel.deleteMeal(meal)
            }

            editBtn.setOnClickListener {
                val action = FragmentHomeDirections.actionFragmentHomeToFragmentAddInMeal(meal.food, meal.weight, true, meal.id)
                findNavController().navigate(action)
            }

            binding.todayMeals.addView(mealView)
        }
    }

}