package com.sanswai.achieve.activity

import android.os.Bundle
import android.os.Handler
import com.sanswai.achieve.R
import android.content.Intent
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences


class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val preferences = Preferences.getInstance(this)
            val isLogin = preferences.getPreferencesBoolean(getString(R.string.is_login), false)
            val userType = preferences.getPreferencesString(getString(R.string.user_type))
            if (isLogin && userType == "employee") {
                val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(i)
                finish()
            } else if (isLogin && userType == "employer") {
                val i = Intent(this@SplashScreenActivity, EmployerDashboardActivity::class.java)
                startActivity(i)
                finish()
            } else {
                val i = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {

        // Splash screen timer
        private val SPLASH_TIME_OUT = 3000
    }

}

