package com.example.anshtest.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.anshtest.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btnLogin->{
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                finish()
            }
        }
    }
}
