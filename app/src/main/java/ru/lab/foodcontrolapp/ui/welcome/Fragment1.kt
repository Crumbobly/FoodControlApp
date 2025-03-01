package ru.lab.foodcontrolapp.ui.welcome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.databinding.FragmentWelcome1Binding
import ru.lab.foodcontrolapp.ui.customview.mypickerview.MyPickerView
import ru.lab.foodcontrolapp.viewmodel.WelcomeViewModel


class Fragment1: Fragment(R.layout.fragment_welcome_1) {

    private lateinit var binding: FragmentWelcome1Binding
    private lateinit var welcomeViewModel: WelcomeViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_welcome_1,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataAges: List<Int> = (1..100).toList()
        val dataHeight: List<Int> = (55..251).toList()

        val agePicker = binding.welcomeAgePicker
        val heightPicker = binding.welcomeHeightPicker
        agePicker.setData(dataAges)
        heightPicker.setData(dataHeight)

        welcomeViewModel = ViewModelProvider(requireActivity())[WelcomeViewModel::class.java]

        val navController = findNavController()

        binding.welcomeBtnNext.setOnClickListener {
            updateViewModel()
            navController.navigate(R.id.action_fragment1_to_fragment2)
        }

        binding.welcomeBtnSkip.setOnClickListener {
            (requireActivity() as WelcomeActivity).skipDataEntry()
        }
    }

    private fun updateViewModel(){
        val age = binding.welcomeAgePicker.getCurrentItem() as Int
        val height = binding.welcomeHeightPicker.getCurrentItem() as Int

        welcomeViewModel.setUserData(
            welcomeViewModel.getUserData().copy(age = age, height = height)
        )
    }


}