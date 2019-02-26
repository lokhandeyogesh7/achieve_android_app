package com.sanswai.achieve.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.android.volley.VolleyError
import com.sanswai.achieve.R
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.network.VolleyService
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity(), View.OnClickListener, VolleyService.SetResponse {

    private var services = VolleyService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        services = VolleyService(this@LoginActivity)

        btnLogin.setOnClickListener(this)
        //btnLoginEmployer.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnLogin -> {
                //startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                //finish()
                if (isOnline) {
                    val loginObject = JSONObject()
                    loginObject.put("email", "kiran.shetti@testemail.com")
                    loginObject.put("password", "kiran123")
                    services.callJsonObjectRequest(getString(R.string.api_login), loginObject)
                    services.mResponseInterface = this
                }else{
                    showNoInternetDialog()
                }
            }
           /* R.id.btnLoginEmployer -> {
                startActivity(Intent(this@LoginActivity, EmployerDashboardActivity::class.java))
                finish()
            }*/
        }
    }


    override fun onSuccess(response: Any) {
        println("login successs ${response}")
    }

    override fun onFailure(volleyError: VolleyError) {
        println("login error ${volleyError.networkResponse}")
    }
}
