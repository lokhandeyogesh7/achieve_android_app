package com.sanswai.achieve.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.android.volley.VolleyError
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employee_login.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.global.Preferences
import java.io.Serializable


class LoginActivity : BaseActivity(), View.OnClickListener, VolleyService.SetResponse {

    private var services = VolleyService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sanswai.achieve.R.layout.activity_login)

        services = VolleyService(this@LoginActivity)

        btnLogin.setOnClickListener(this)
        //btnLoginEmployer.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            com.sanswai.achieve.R.id.btnLogin -> {
                //startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                //finish()
                if (isOnline) {
                    val loginObject = JSONObject()
                    loginObject.put("email", "kiran.shetti@testemail.com")
                    loginObject.put("password", "kiran123")
                    /*loginObject.put("email", "employer2@employer.com")
                    loginObject.put("password", "employer123")*/
                    services.callJsonObjectRequest(getString(com.sanswai.achieve.R.string.api_login), loginObject)
                    services.mResponseInterface = this
                } else {
                    showNoInternetDialog()
                }
            }
            /* R.id.btnLoginEmployer -> {
                 startActivity(Intent(this@LoginActivity, EmployerDashboardActivity::class.java))
                 finish()
             }*/
        }
    }


    override fun onSuccess(methodName: String, response: Any) {
        val jsonObject = response as JSONObject
        if (jsonObject.getBoolean("response")) {
            if (jsonObject.getString("user_type").equals("employee", true)) {
                val loginResponse = Gson().fromJson(response.toString(), LoginResponse::class.java)
                println("login successs $loginResponse")
                val preferences = Preferences.getInstance(this)
                preferences.setPreferencesBody(getString(R.string.user_type), loginResponse!!.userType!!)
                preferences.setPreferencesBody(getString(R.string.is_login),true)

                println("user type from preferences ${preferences.getPreferencesString("user_type")}")
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra(getString(R.string.employee_data), loginResponse as Serializable)
                startActivity(intent)
                finish()
            } else {
                val loginResponse = Gson().fromJson(response.toString(), LoginResponse::class.java)
                val intent = Intent(this@LoginActivity, EmployerDashboardActivity::class.java)
                intent.putExtra("employee_data", loginResponse as Serializable)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
        println("login error ${volleyError.networkResponse}")
    }
}
