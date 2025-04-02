package ru.lab.foodcontrolapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.databinding.FragmentAddInMealBinding
import ru.lab.foodcontrolapp.viewmodel.AddInMealViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBMealViewModel
import ru.lab.foodcontrolapp.viewmodel.factory.AddInMealViewModelFactory

class FragmentAddInMeal: Fragment(R.layout.fragment_add_in_meal) {

    private lateinit var binding: FragmentAddInMealBinding
    private lateinit var args: FragmentAddInMealArgs

    private val _dbMealViewModel: DBMealViewModel by viewModels()
    private val addInMealViewModel: AddInMealViewModel by viewModels {
        AddInMealViewModelFactory(
            _dbMealViewModel,
            args.isEdit,
            args.id
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = FragmentAddInMealArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_in_meal,
            container,
            false
        )

        binding.textView.text = args.food.name
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mealWeightEdit.addTextChangedListener { text ->
            addInMealViewModel.onWeightChanged(text.toString())
        }
        if (args.weight != -1) {
            binding.mealWeightEdit.setText("${args.weight}")
        }

        binding.addInMealBtn.setOnClickListener {
            addInMealViewModel.onAddBtnClicked(args.food)
        }

        binding.backBtn.setOnClickListener {
            addInMealViewModel.onBackBtnClicked()
        }

        addInMealViewModel.mealWeight.observe(viewLifecycleOwner) { weightInt ->
            val currentText = binding.mealWeightEdit.text.toString()
            val newText = weightInt.toString()

            if (currentText != newText) {
                binding.mealWeightEdit.setText(newText)
                binding.mealWeightEdit.setSelection(newText.length) // Курсор в конец
            }
        }

        addInMealViewModel.addMeal.observe(viewLifecycleOwner){
            state ->
            if (state){
                val action = FragmentAddInMealDirections.actionFragmentAddInMealToFragmentHome()
                findNavController().navigate(action)
                addInMealViewModel.onNavigationDone()
            }
        }

        addInMealViewModel.navigateBack.observe(viewLifecycleOwner){
            state ->
            if (state){
                findNavController().popBackStack()
                addInMealViewModel.onNavigationDone()
            }
        }


    }




}