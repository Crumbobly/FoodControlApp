package ru.lab.foodcontrolapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.data.database.entity.Food
import ru.lab.foodcontrolapp.databinding.FragmentFoodDetailBinding

class FragmentFoodDetail: Fragment(R.layout.fragment_food_detail) {

    private lateinit var binding: FragmentFoodDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_food_detail,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val food = arguments?.getParcelable<Food>("food")
        if (food != null) {
            binding.foodName.text = food.name
            binding.calories.text = "${food.calories}"
            binding.proteins.text = "${food.protein}"
            binding.fats.text = "${food.fat}"
            binding.carbs.text = "${food.carbs}"
        }

        binding.close.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}