package ru.lab.foodcontrolapp.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.data.database.entity.User
import ru.lab.foodcontrolapp.databinding.FragmentMainSettingsBinding
import ru.lab.foodcontrolapp.ui.customview.userdatainput.UserDataInput
import ru.lab.foodcontrolapp.viewmodel.SettingsViewModel
import ru.lab.foodcontrolapp.viewmodel.UserDataInputViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel
import ru.lab.foodcontrolapp.viewmodel.factory.SettingsViewModelFactory

class FragmentSettings : Fragment(R.layout.fragment_main_settings) {

    private lateinit var binding: FragmentMainSettingsBinding

    private val _dbUserViewModel: DBUserViewModel by viewModels()
    private val _userDataInputViewModel: UserDataInputViewModel by viewModels()

    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(
            _userDataInputViewModel.userData,
            _dbUserViewModel.userData,
            _dbUserViewModel.isUserDataComplete,
            _dbUserViewModel
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_settings,
            container,
            false
        )

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userDataInput =
            childFragmentManager.findFragmentById(R.id.fragmentContainerView) as UserDataInput
        userDataInput.setUserDataInputViewModel(_userDataInputViewModel)

        binding.settingsBtnEditUser.setOnClickListener {
            settingsViewModel.onBtnEditUserPressed()
            animateButtonSwap(
                binding.settingsBtnEditUser,
                binding.settingsBtnSaveUser,
                binding.settingsBtnCancelUser,
                true
            )
            userDataInput.setEnabled(true)
        }
        binding.settingsBtnSaveUser.setOnClickListener {
            settingsViewModel.onBtnSaveUserPressed()
            animateButtonSwap(
                binding.settingsBtnSaveUser,
                binding.settingsBtnEditUser,
                binding.settingsBtnCancelUser,
                false
            )
            userDataInput.setEnabled(false)
        }
        binding.settingsBtnCancelUser.setOnClickListener {
            settingsViewModel.onBtnCancelUserPressed()
            animateButtonSwap(
                binding.settingsBtnSaveUser,
                binding.settingsBtnEditUser,
                binding.settingsBtnCancelUser,
                false
            )
            userDataInput.setEnabled(false)
        }

        binding.settingsBtnEditKcal.setOnClickListener {
            settingsViewModel.onBtnEditKCalPressed()
            animateButtonSwap(
                binding.settingsBtnEditKcal,
                binding.settingsBtnSaveKcal,
                binding.settingsBtnCancelKcal,
                true
            )
            setEnabledKcalInput(true)
        }
        binding.settingsBtnSaveKcal.setOnClickListener {
            settingsViewModel.onBtnSaveKCalPressed()
            animateButtonSwap(
                binding.settingsBtnSaveKcal,
                binding.settingsBtnEditKcal,
                binding.settingsBtnCancelKcal,
                false
            )
            setEnabledKcalInput(false)
        }
        binding.settingsBtnCancelKcal.setOnClickListener {
            settingsViewModel.onBtnCancelKCalPressed()
            animateButtonSwap(
                binding.settingsBtnSaveKcal,
                binding.settingsBtnEditKcal,
                binding.settingsBtnCancelKcal,
                false
            )
            setEnabledKcalInput(false)
        }
        binding.updateCaloriesBtn.setOnClickListener {
            settingsViewModel.onUpdateCaloriesBtnPressed()
        }
        binding.btn1Up.setOnClickListener {
            settingsViewModel.changeLocalCalories(true, 0)
        }
        binding.btn1Down.setOnClickListener {
            settingsViewModel.changeLocalCalories(false, 0)
        }
        binding.btn2Up.setOnClickListener {
            settingsViewModel.changeLocalCalories(true, 1)
        }
        binding.btn2Down.setOnClickListener {
            settingsViewModel.changeLocalCalories(false, 1)
        }
        binding.btn3Up.setOnClickListener {
            settingsViewModel.changeLocalCalories(true, 2)
        }
        binding.btn3Down.setOnClickListener {
            settingsViewModel.changeLocalCalories(false, 2)
        }
        binding.btn4Up.setOnClickListener {
            settingsViewModel.changeLocalCalories(true, 3)
        }
        binding.btn4Down.setOnClickListener {
            settingsViewModel.changeLocalCalories(false, 3)
        }

        settingsViewModel.userDataGlobal.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                userDataInput.setUserData(user)
                settingsViewModel.setLocalCalories(user.calories)
            }
            userDataInput.setEnabled(false)
            setEnabledKcalInput(false)
        }

        settingsViewModel.isUserDataComplete.observe(viewLifecycleOwner) { state ->
            setVisibleWarningBlock1(state)
        }
        settingsViewModel.resetUser.observe(viewLifecycleOwner) { user ->
            resetUser(user)
        }
        settingsViewModel.resetCalories.observe(viewLifecycleOwner) { calories ->
            resetCalories(calories)
        }

    }

    private fun setEnabledKcalInput(state: Boolean) {
        binding.btn1Up.isEnabled = state
        binding.btn1Down.isEnabled = state
        binding.btn2Up.isEnabled = state
        binding.btn2Down.isEnabled = state
        binding.btn3Up.isEnabled = state
        binding.btn3Down.isEnabled = state
        binding.btn4Up.isEnabled = state
        binding.btn4Down.isEnabled = state

        binding.btn1Up.isInvisible = !state
        binding.btn1Down.isInvisible = !state
        binding.btn2Up.isInvisible = !state
        binding.btn2Down.isInvisible = !state
        binding.btn3Up.isInvisible = !state
        binding.btn3Down.isInvisible = !state
        binding.btn4Up.isInvisible = !state
        binding.btn4Down.isInvisible = !state
    }

    private fun resetCalories(n: Int) {
        val digits = n.toString().padStart(4, '0').map { it.toString().toInt() }
        binding.cal1.text = "${digits[0]}"
        binding.cal2.text = "${digits[1]}"
        binding.cal3.text = "${digits[2]}"
        binding.cal4.text = "${digits[3]}"
    }

    private fun resetUser(user: User) {
        val userDataInput =
            childFragmentManager.findFragmentById(R.id.fragmentContainerView) as UserDataInput

        userDataInput.setUserData(user)
    }

    private fun setVisibleWarningBlock1(visible: Boolean) {
        when (visible) {
            true -> binding.settingsWarningBlock1.visibility = View.GONE
            false -> binding.settingsWarningBlock1.visibility = View.VISIBLE
        }
    }

    private fun animateButtonSwap(
        fromButton: View,
        toButton: View,
        buttonCancel: View,
        isEditClick: Boolean
    ) {

        val fadeOut = ObjectAnimator.ofFloat(fromButton, View.ALPHA, 1f, 0f).apply {
            duration = 200
        }
        val fadeIn = ObjectAnimator.ofFloat(toButton, View.ALPHA, 0f, 1f).apply {
            duration = 200
        }
        val fadeInCancel = ObjectAnimator.ofFloat(buttonCancel, View.ALPHA, 0f, 1f).apply {
            duration = 200
        }
        val fadeOutCancel = ObjectAnimator.ofFloat(buttonCancel, View.ALPHA, 1f, 0f).apply {
            duration = 200
        }

        fadeOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                fromButton.visibility = View.INVISIBLE
                toButton.visibility = View.VISIBLE
            }
        })

        fadeInCancel.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                buttonCancel.visibility = View.VISIBLE
            }
        })

        fadeOutCancel.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                buttonCancel.visibility = View.INVISIBLE
            }
        })

        // Запускаем все анимации одновременно
        AnimatorSet().apply {
            playTogether(fadeOut, fadeIn, if (isEditClick) fadeInCancel else fadeOutCancel)
            start()
        }
    }


}