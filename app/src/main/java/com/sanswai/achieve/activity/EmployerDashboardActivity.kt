package com.sanswai.achieve.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.error.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.adapter.EmployeeDashboardAdapter
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employerdashboard.Datum
import com.sanswai.achieve.response.employerdashboard.EmployerDashboardResponse
import com.sanswai.achieve.response.employerlogin.EmployerLoginResponse
import kotlinx.android.synthetic.main.activity_employer_dashbaord.*
import org.json.JSONObject

class EmployerDashboardActivity : BaseActivity(), VolleyService.SetResponse {

    var employeeList: ArrayList<Datum>? = null
    var adapter: EmployeeDashboardAdapter? = null
    var loginResponse: EmployerLoginResponse? = null
    var services: VolleyService? = null
    lateinit var preferences: Preferences
    var userId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employer_dashbaord)

        preferences = Preferences.getInstance(this)
        services = VolleyService(this)
        userId = preferences.getPreferencesInt(getString(R.string.user_id), 0)

       // loginResponse = intent.getSerializableExtra(getString(R.string.employee_data)) as EmployerLoginResponse

        setupActionBar()


        getEmployerDashboardData()
    }

    private fun getEmployerDashboardData() {
        services!!.callJsonObjectRequest(getString(R.string.api_employer_dashboard)+userId, JSONObject())
        services!!.mResponseInterface = this
    }

    override fun onSuccess(methodName: String, response: Any) {
        val emploerResponse = Gson().fromJson(response.toString(), EmployerDashboardResponse::class.java)

        employeeList = ArrayList()
        employeeList = emploerResponse.data as ArrayList<Datum>?
        adapter = EmployeeDashboardAdapter(this@EmployerDashboardActivity, employeeList)

        val mLayoutManager = LinearLayoutManager(this@EmployerDashboardActivity, LinearLayoutManager.VERTICAL, false)
        rvEmployeeList.layoutManager = mLayoutManager
        rvEmployeeList.itemAnimator = DefaultItemAnimator()
        rvEmployeeList.adapter = adapter
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
        println("error is $methodName error is $volleyError")
    }


    private fun setupActionBar() {
        //set title to the activity
        supportActionBar!!.title = "Dashboard"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //action to only action menu button
        when (item!!.itemId) {
            R.id.logout -> {
                preferences!!.clearPreferences()
                Toast.makeText(this, "You are successfully logged out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@EmployerDashboardActivity, LoginActivity::class.java))
                finish()
                return true
            }
            else -> {
                onBackPressed()
                return true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu!!.findItem(R.id.profile_menu).isVisible = false;
        return super.onCreateOptionsMenu(menu)
    }

}
