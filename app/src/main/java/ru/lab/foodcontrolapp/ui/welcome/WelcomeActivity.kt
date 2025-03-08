package ru.lab.foodcontrolapp.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.databinding.ActivityWelcomeBinding
import ru.lab.foodcontrolapp.ui.main.MainActivity
import ru.lab.foodcontrolapp.viewmodel.UserDataInputViewModel
import ru.lab.foodcontrolapp.viewmodel.UserViewModel
import ru.lab.foodcontrolapp.viewmodel.WelcomeViewModel

/**
 * Класс для activity страницы приветствия.
 */
class WelcomeActivity: AppCompatActivity(){

    private lateinit var binding: ActivityWelcomeBinding
    private val welcomeViewModel: WelcomeViewModel by viewModels()
    private val userDataInputViewModel: UserDataInputViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        binding.welcomeBtnCancel.setOnClickListener {
            welcomeViewModel.onBtnCancelPressed()
        }

        binding.welcomeBtnNext.setOnClickListener {
            welcomeViewModel.onBtnNextPressed()
        }

        userDataInputViewModel.userFullyInput.observe(this){
            binding.welcomeBtnNext.isEnabled = true
        }

        welcomeViewModel.onChangeToMain.observe(this) {
            changeActivityToMain()
        }

        welcomeViewModel.onUserToSave.observe(this) {
            userDataInputViewModel.saveGlobalUser(userViewModel)
        }
    }

    private fun changeActivityToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}