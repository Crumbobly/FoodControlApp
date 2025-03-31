package ru.lab.foodcontrolapp.ui.customview.userdatainput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.data.database.entity.Gender
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.databinding.CwViewUserDataInputBinding
import ru.lab.foodcontrolapp.viewmodel.UserDataInputViewModel

class UserDataInput: Fragment(R.layout.cw_view_user_data_input) {

    private lateinit var binding: CwViewUserDataInputBinding
    private lateinit var userDataInputViewModel: UserDataInputViewModel

    fun setUserDataInputViewModel(userDataInputViewModel: UserDataInputViewModel){
        this.userDataInputViewModel = userDataInputViewModel
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.cw_view_user_data_input,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        // Устанавливаем списки значений в Picker
        binding.agePickerView.setData(userDataInputViewModel.dataAges)
        binding.heightPickerView.setData(userDataInputViewModel.dataHeight)
        binding.weightPickerView.setData(userDataInputViewModel.dataWeight)

        // Устанавливаем обработчики для обновления локальных данных
        binding.agePickerView.setOnItemSelectedListener {
            age -> userDataInputViewModel.updateLocalUser(age = age as Int)
        }
        binding.heightPickerView.setOnItemSelectedListener {
            height -> userDataInputViewModel.updateLocalUser(height = height as Int)
        }
        binding.weightPickerView.setOnItemSelectedListener {
            weight -> userDataInputViewModel.updateLocalUser(weight = weight as Int)
        }

        binding.radioFemale.setOnClickListener {
            userDataInputViewModel.updateLocalUser(gender = Gender.FEMALE)
        }
        binding.radioMale.setOnClickListener {
            userDataInputViewModel.updateLocalUser(gender = Gender.MALE)
        }
        binding.radioOther.setOnClickListener {
            userDataInputViewModel.updateLocalUser(gender = Gender.OTHER)
        }

        userDataInputViewModel.userData.observe(viewLifecycleOwner){ user ->
            setRadioButtons(user)
        }

    }

    private fun setRadioButtons(user: User){
        binding.radioFemale.isChecked = user.gender == Gender.FEMALE
        binding.radioMale.isChecked = user.gender == Gender.MALE
        binding.radioOther.isChecked = user.gender == Gender.OTHER
    }

    fun setUserData(user: User) {
        userDataInputViewModel.setLocalUser(user)
        binding.agePickerView.setCurrentItem(user.age)
        binding.heightPickerView.setCurrentItem(user.height)
        binding.weightPickerView.setCurrentItem(user.weight)
        setRadioButtons(user)
    }

    fun setEnabled(state: Boolean = true) {
        binding.blockViewPicker.visibility = if (state) View.GONE else View.VISIBLE
        binding.radioFemale.isEnabled = state
        binding.radioMale.isEnabled = state
        binding.radioOther.isEnabled = state
        binding.agePickerView.setVisualEnabled(state)
        binding.weightPickerView.setVisualEnabled(state)
        binding.heightPickerView.setVisualEnabled(state)
    }


}
