package ru.lab.foodcontrolapp.ui.blank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ru.lab.foodcontrolapp.ui.main.MainActivity
import ru.lab.foodcontrolapp.ui.welcome.WelcomeActivity

class BlankActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Проверка, первый ли это запуск приложения
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)
        Log.d("BlankActivity", isFirstLaunch.toString())

        if (isFirstLaunch) {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        finish()
    }



}