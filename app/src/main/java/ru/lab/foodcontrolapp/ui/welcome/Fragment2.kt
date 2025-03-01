package ru.lab.foodcontrolapp.ui.welcome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.data.database.entity.Gender
import ru.lab.foodcontrolapp.databinding.FragmentWelcome2Binding
import ru.lab.foodcontrolapp.viewmodel.WelcomeViewModel

class Fragment2: Fragment(R.layout.fragment_welcome_2) {

    private lateinit var binding: FragmentWelcome2Binding
    private lateinit var welcomeViewModel: WelcomeViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_welcome_2,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataWeight: List<Int> = (1..250).toList()
        val weightPicker = binding.welcomeWeightPicker
        weightPicker.setData(dataWeight)

        welcomeViewModel = ViewModelProvider(requireActivity())[WelcomeViewModel::class.java]

        val navController = findNavController()

        binding.welcomeBtnBack.setOnClickListener {
            navController.popBackStack(R.id.fragment1, inclusive = false, saveState = true)
        }

        binding.welcomeBtnFinish.setOnClickListener {
            updateViewModel()
            (requireActivity() as WelcomeActivity).finishDataEntry()
        }

        binding.welcomeRadioFemale.setOnClickListener{
            binding.welcomeRadioMale.isChecked = false
            binding.welcomeRadioOther.isChecked = false
        }

        binding.welcomeRadioMale.setOnClickListener{
            binding.welcomeRadioFemale.isChecked = false
            binding.welcomeRadioOther.isChecked = false
        }

        binding.welcomeRadioOther.setOnClickListener{
            binding.welcomeRadioFemale.isChecked = false
            binding.welcomeRadioMale.isChecked = false
        }
    }

    private fun getGender(): Gender{
        return if (binding.welcomeRadioFemale.isChecked)
            Gender.FEMALE
        else if (binding.welcomeRadioMale.isChecked)
            Gender.MALE
        else
            Gender.OTHER
    }

    private fun updateViewModel(){
        val weight = binding.welcomeWeightPicker.getCurrentItem() as Int
        val gender = getGender()

        welcomeViewModel.setUserData(
            welcomeViewModel.getUserData().copy(weight = weight, gender = gender)
        )
    }

}