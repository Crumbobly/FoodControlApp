package ru.lab.foodcontrolapp.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.databinding.ActivityWelcomeBinding
import ru.lab.foodcontrolapp.ui.customview.userdatainput.UserDataInput
import ru.lab.foodcontrolapp.ui.main.MainActivity
import ru.lab.foodcontrolapp.viewmodel.UserDataInputViewModel
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel
import ru.lab.foodcontrolapp.viewmodel.WelcomeViewModel
import ru.lab.foodcontrolapp.viewmodel.factory.WelcomeViewModelFactory

/**
 * Класс для activity страницы приветствия.
 */
class WelcomeActivity: AppCompatActivity(){

    private lateinit var binding: ActivityWelcomeBinding

    private val _userDataInputViewModel: UserDataInputViewModel by viewModels()
    private val _dbUserViewModel: DBUserViewModel by viewModels()

    private val welcomeViewModel: WelcomeViewModel by viewModels {
        WelcomeViewModelFactory(
            application,
            _userDataInputViewModel.userData,
            _userDataInputViewModel.userFullyInput,
            _dbUserViewModel
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        val userDataInput = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as UserDataInput
        userDataInput.setUserDataInputViewModel(_userDataInputViewModel)

        binding.welcomeBtnCancel.setOnClickListener {
            welcomeViewModel.onBtnCancelPressed()
        }
        binding.welcomeBtnNext.setOnClickListener {
            welcomeViewModel.onBtnNextPressed()
        }

        welcomeViewModel.userDataLocalIsFullyInput.observe(this){
            state -> binding.welcomeBtnNext.isEnabled = state
        }
        welcomeViewModel.onChangeToMain.observe(this) {
            changeActivityToMain()
        }

    }

    private fun changeActivityToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}