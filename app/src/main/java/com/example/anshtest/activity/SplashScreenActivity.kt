package com.example.anshtest.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.anshtest.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.app.Activity


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

