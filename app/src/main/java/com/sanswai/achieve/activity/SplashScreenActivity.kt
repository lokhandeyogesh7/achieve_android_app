package com.sanswai.achieve.activity

import android.os.Bundle
import android.os.Handler
import com.sanswai.achieve.R
import android.content.Intent
import com.sanswai.achieve.global.BaseActivity


class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(i)

            // close this activity
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {

        // Splash screen timer
        private val SPLASH_TIME_OUT = 3000
    }

}

