package ru.lab.foodcontrolapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.databinding.ActivityMainBinding
import ru.lab.foodcontrolapp.ui.welcome.WelcomeActivity
import ru.lab.foodcontrolapp.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()

    private lateinit var fragmentHome: FragmentHome
    private lateinit var fragmentSettings: FragmentSettings
    private lateinit var fragmentStats: FragmentStats
    private lateinit var fragmentFoodBase: FragmentFoodBase

    private lateinit var currentFragment: Fragment

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentFragmentTag = when (currentFragment) {
            fragmentHome -> "home"
            fragmentSettings -> "settings"
            fragmentStats -> "stats"
            fragmentFoodBase -> "foodBase"
            else -> "home"
        }
        outState.putString("currentFragmentTag", currentFragmentTag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val currentFragmentTag = savedInstanceState.getString("currentFragmentTag")

        when (currentFragmentTag) {
            "home" -> currentFragment = fragmentHome
            "settings" -> currentFragment = fragmentSettings
            "stats" -> currentFragment = fragmentStats
            "foodBase" -> currentFragment = fragmentFoodBase
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val bottomNavMenu = binding.mainBottomMenu


        val fragmentManager = supportFragmentManager
        fragmentHome = (fragmentManager.findFragmentByTag("home") ?: FragmentHome()) as FragmentHome
        fragmentSettings = (fragmentManager.findFragmentByTag("settings") ?: FragmentSettings()) as FragmentSettings
        fragmentStats = (fragmentManager.findFragmentByTag("stats") ?: FragmentStats()) as FragmentStats
        fragmentFoodBase = (fragmentManager.findFragmentByTag("foodBase") ?: FragmentFoodBase()) as FragmentFoodBase

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                .add(R.id.container, fragmentSettings, "settings").hide(fragmentSettings)
                .add(R.id.container, fragmentStats, "stats").hide(fragmentStats)
                .add(R.id.container, fragmentFoodBase, "foodBase").hide(fragmentFoodBase)
                .add(R.id.container, fragmentHome, "home")
                .commit()
        }

        currentFragment = fragmentHome
        bottomNavMenu.selectedItemId = when (currentFragment) {
            fragmentHome -> R.id.fragmentHome
            fragmentSettings -> R.id.fragmentSettings
            fragmentStats -> R.id.fragmentStats
            fragmentFoodBase -> R.id.fragmentFoodBase
            else -> R.id.fragmentHome
        }

        bottomNavMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragmentHome -> switchFragment(fragmentHome)
                R.id.fragmentSettings -> switchFragment(fragmentSettings)
                R.id.fragmentStats -> switchFragment(fragmentStats)
                R.id.fragmentFoodBase -> switchFragment(fragmentFoodBase)
                else -> false
            }
        }



        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }
    }

    private fun switchFragment(targetFragment: Fragment): Boolean {
        if (targetFragment == currentFragment) return false

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .hide(currentFragment)
            .show(targetFragment)
            .commit()

        currentFragment = targetFragment
        return true
    }




}