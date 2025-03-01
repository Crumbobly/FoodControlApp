package ru.lab.foodcontrolapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.databinding.ActivityMainBinding
import ru.lab.foodcontrolapp.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val bottomNavMenu = binding.mainBottomMenu
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavMenu.setupWithNavController(navController)

//        val bottomNavigationView = binding.mainBottomMenu
//        bottomNavigationView.setupWithNavController(navController)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


}