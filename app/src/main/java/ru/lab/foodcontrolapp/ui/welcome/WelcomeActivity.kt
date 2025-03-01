package ru.lab.foodcontrolapp.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.data.database.AppDatabase
import ru.lab.foodcontrolapp.databinding.ActivityWelcomeBinding
import ru.lab.foodcontrolapp.viewmodel.UserViewModel
import ru.lab.foodcontrolapp.data.database.repositiry.UserRepository
import ru.lab.foodcontrolapp.ui.main.MainActivity
import ru.lab.foodcontrolapp.viewmodel.UserViewModelFactory
import ru.lab.foodcontrolapp.viewmodel.WelcomeViewModel

/**
 * Класс для activity страницы приветствия.
 */
class WelcomeActivity: AppCompatActivity(){

    // DataBinding
    private lateinit var binding: ActivityWelcomeBinding
    // Контроллер навигации
    private lateinit var navController: NavController
    // ViewModel пользователя (контекст всего приложения)
    private lateinit var userViewModel: UserViewModel
    // ViewModel пользователя (контекст WelcomeActivity)
    private lateinit var welcomeViewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.WelcomeTheme)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_welcome) as NavHostFragment
        navController = navHostFragment.navController

        val database = AppDatabase.getInstance(this.application)
        val repository = UserRepository(database.getUserDao())
        val factory = UserViewModelFactory(repository, this.application)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        welcomeViewModel = ViewModelProvider(this)[WelcomeViewModel::class.java]
    }

    fun skipDataEntry() {
        changeActivityToMain()
    }


    fun finishDataEntry() {
        userViewModel.updateUser(welcomeViewModel.getUserData())
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
        changeActivityToMain()
    }

    private fun changeActivityToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}