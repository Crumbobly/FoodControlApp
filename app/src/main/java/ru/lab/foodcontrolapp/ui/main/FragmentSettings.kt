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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.databinding.FragmentMainSettingsBinding
import ru.lab.foodcontrolapp.ui.customview.userdatainput.UserDataInput
import ru.lab.foodcontrolapp.viewmodel.UserDataInputViewModel
import ru.lab.foodcontrolapp.viewmodel.UserViewModel

class FragmentSettings : Fragment(R.layout.fragment_main_settings) {

    private lateinit var binding: FragmentMainSettingsBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val userDataInputViewModel: UserDataInputViewModel by activityViewModels()

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            clearFields()
            binding.settingsBtnEdit.visibility = View.VISIBLE
            binding.settingsBtnEdit.alpha = 1f
            binding.settingsBtnSave.visibility = View.INVISIBLE
            binding.settingsBtnSave.alpha = 0f
            binding.settingsBtnCancel.visibility = View.INVISIBLE
            binding.settingsBtnCancel.alpha = 0f
            Log.d("FragmentSettings", "is hidden")
        }
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

        val userDataInput = childFragmentManager.findFragmentById(R.id.fragmentContainerView) as UserDataInput
        userViewModel.userData.observe(this as LifecycleOwner) { user ->
            if (user != null)
                userDataInput.setUserData(user)
            userDataInput.setEnabled(false)
        }

        binding.settingsBtnEdit.setOnClickListener {
            animateButtonSwap(true)
            clearFields()
            userDataInput.setEnabled(true)
        }
        binding.settingsBtnSave.setOnClickListener {
            animateButtonSwap(false)
            saveUser()
            userDataInput.setEnabled(false)
        }
        binding.settingsBtnCancel.setOnClickListener {
            animateButtonSwap(false)
            clearFields()
            userDataInput.setEnabled(false)
        }
    }

    private fun saveUser() {
        userDataInputViewModel.userData.value?.let { userViewModel.insertUserToDatabase(it) }
    }

    private fun clearFields() {
        val userDataInput = childFragmentManager.findFragmentById(R.id.fragmentContainerView) as UserDataInput
        userViewModel.userData.value?.let { userDataInput.setUserData(it) }
        userDataInput.setEnabled(false)
    }


    private fun animateButtonSwap(isEditClick: Boolean) {
        val fromButton: View
        val toButton: View
        val buttonCancel = binding.settingsBtnCancel

        if (isEditClick) {
            fromButton = binding.settingsBtnEdit
            toButton = binding.settingsBtnSave
        } else {
            fromButton = binding.settingsBtnSave
            toButton = binding.settingsBtnEdit
        }

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