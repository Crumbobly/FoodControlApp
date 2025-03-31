package ru.lab.foodcontrolapp.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.databinding.ActivityMainBinding
import ru.lab.foodcontrolapp.ui.blank.LoadingFragment
import ru.lab.foodcontrolapp.viewmodel.appcontext.DBUserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    private lateinit var fragmentHome: FragmentHome
//    private lateinit var fragmentSettings: FragmentSettings
//    private lateinit var fragmentStats: FragmentStats
//    private lateinit var fragmentFoodBase: FragmentFoodBase
//
//    private lateinit var currentFragment: Fragment

//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        val currentFragmentTag = when (currentFragment) {
//            fragmentHome -> "home"
//            fragmentSettings -> "settings"
//            fragmentStats -> "stats"
//            fragmentFoodBase -> "foodBase"
//            else -> "home"
//        }
//        outState.putString("currentFragmentTag", currentFragmentTag)
//    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        val currentFragmentTag = savedInstanceState.getString("currentFragmentTag")
//
//        when (currentFragmentTag) {
//            "home" -> currentFragment = fragmentHome
//            "settings" -> currentFragment = fragmentSettings
//            "stats" -> currentFragment = fragmentStats
//            "foodBase" -> currentFragment = fragmentFoodBase
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.nav_host_main)

        val bottomNavMenu = binding.mainBottomMenu
        bottomNavMenu.setupWithNavController(navController)


//        dbUserViewModel.userData.observe(this){
//            user -> // Просто чтобы заинитить viewmodel
//        }

//        val fragmentManager = supportFragmentManager
//        fragmentHome = (fragmentManager.findFragmentByTag("home") ?: FragmentHome()) as FragmentHome
//        fragmentSettings = (fragmentManager.findFragmentByTag("settings") ?: FragmentSettings()) as FragmentSettings
//        fragmentStats = (fragmentManager.findFragmentByTag("stats") ?: FragmentStats()) as FragmentStats
//        fragmentFoodBase = (fragmentManager.findFragmentByTag("foodBase") ?: FragmentFoodBase()) as FragmentFoodBase

//        if (savedInstanceState == null) {
//            fragmentManager.beginTransaction()
//                .add(R.id.container, fragmentSettings, "settings").hide(fragmentSettings)
//                .add(R.id.container, fragmentStats, "stats").hide(fragmentStats)
//                .add(R.id.container, fragmentFoodBase, "foodBase").hide(fragmentFoodBase)
//                .add(R.id.container, fragmentHome, "home")
//                .commit()
//        }

//        currentFragment = fragmentHome
//        bottomNavMenu.selectedItemId = when (currentFragment) {
//            fragmentHome -> R.id.fragmentHome
//            fragmentSettings -> R.id.fragmentSettings
//            fragmentStats -> R.id.fragmentStats
//            fragmentFoodBase -> R.id.fragmentFoodBase
//            else -> R.id.fragmentHome
//        }
//
//        bottomNavMenu.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.fragmentHome -> switchFragment(fragmentHome)
//                R.id.fragmentSettings -> switchFragment(fragmentSettings)
//                R.id.fragmentStats -> switchFragment(fragmentStats)
//                R.id.fragmentFoodBase -> switchFragment(fragmentFoodBase)
//                else -> false
//            }
//        }


//        DBUserViewModel.isUserDataComplete.observe(this) { isComplete ->
//            val menu = binding.mainBottomMenu.menu
//            val settingsItem = menu.findItem(R.id.fragmentSettings)
//
//            val badge = binding.mainBottomMenu.getOrCreateBadge(settingsItem.itemId)
//
//            if (isComplete) {
//                badge.isVisible = false
//            } else {
//                badge.isVisible = true
//                badge.backgroundColor = getColor(R.color.colorPrimary)
//                badge.clearNumber()
//                badge.text = "!"
//            }
//        }

//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                if (currentFragment != fragmentHome) {
//                    bottomNavMenu.selectedItemId = R.id.fragmentHome
//                } else {
//                    finish()
//                }
//            }
//        })

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }
    }


//    private fun switchFragment(targetFragment: Fragment): Boolean {
//
//        if (targetFragment == currentFragment) return false
//
//        val transaction = supportFragmentManager.beginTransaction()
//
//        // Показываем анимацию загрузки
//        val loadingFragment = LoadingFragment()
//        transaction.replace(R.id.container, loadingFragment)
//        transaction.commit()
//
//        // Запускаем загрузку данных асинхронно
//        Handler(Looper.getMainLooper()).postDelayed({
//            val newTransaction = supportFragmentManager.beginTransaction()
//            newTransaction.replace(R.id.container, targetFragment)
//            newTransaction.commit()
//        }, 0)
//
//        currentFragment = targetFragment
//        return true
//    }

}