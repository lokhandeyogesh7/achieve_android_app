package com.example.anshtest.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.anshtest.network.VolleyService

open class BaseActivity : AppCompatActivity() {

    var mVolleyService: VolleyService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVolleyService = VolleyService()
    }


    /**
     * @param message
     */
    fun showToast(message: String) {
        Toast.makeText(this@BaseActivity, message, Toast.LENGTH_SHORT).show()
    }

}