package com.sanswai.achieve.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.sanswai.achieve.R
import android.widget.Toast
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.adapter.EmployerListAdapter
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeelogin.UserData
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : BaseActivity(), VolleyService.SetResponse {
    //private var loginResponse: LoginResponse? = null
    var userId: Int? = null
    var preferences: Preferences? = null
    var services: VolleyService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //loginResponse = intent.getSerializableExtra(getString(R.string.employee_data)) as LoginResponse?
        preferences = Preferences.getInstance(this)
        services = VolleyService(this)
        userId = preferences!!.getPreferencesInt(getString(R.string.user_id), 0)


        getEmployeeDashboard()

        setUpActionBar()
    }

    private fun getEmployeeDashboard() {
        services!!.callJsonObjectRequest(getString(R.string.employee_dashboard)+userId, JSONObject())
        services!!.mResponseInterface = this
    }

    private fun setUpActionBar() {
        //set title to activity
        supportActionBar!!.title = "Dashboard"
    }

    override fun onSuccess(methodName: String, response: Any) {

        val userData = Gson().fromJson(response.toString(), UserData::class.java)

        tvMainEmpName.text = preferences!!.getPreferencesString(getString(R.string.username))
        rbMainEmp.rating = userData.averageRating!!.toFloat()
        tvMainAvgReview.text = userData.performanceStatus

        rvEmployerList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        //set adapter for recycler view
        rvEmployerList.adapter = EmployerListAdapter(userData.data) { result ->
            //call new activity to show details and pass uid to particular activity
            val intent = Intent(this@MainActivity, ReviewDetailsActivity::class.java)
            intent.putExtra(getString(R.string.employer_id), result.id)
            startActivity(intent)
        }
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.profile_menu -> {
                startActivity(Intent(this@MainActivity, EmpProfileActivity::class.java))
                return true
            }

            R.id.logout -> {
                Toast.makeText(this, "You are successfully logged out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
                return true
            }
        }
        return true
    }
}
