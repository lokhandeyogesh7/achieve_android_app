package com.sanswai.achieve.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sanswai.achieve.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener(this)
        btnLoginEmployer.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btnLogin->{
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                finish()
            }
            R.id.btnLoginEmployer->{
                startActivity(Intent(this@LoginActivity,EmployerDashboardActivity::class.java))
                finish()
            }
        }
    }
}
