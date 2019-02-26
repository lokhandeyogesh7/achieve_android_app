package com.sanswai.achieve.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.sanswai.achieve.R
import android.widget.Toast
import com.sanswai.achieve.adapter.EmployerListAdapter
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.model.Result
import com.sanswai.achieve.response.employee_login.LoginResponse
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private var loginResponse: LoginResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginResponse = intent.getSerializableExtra(getString(R.string.employee_data)) as LoginResponse?

        setUpActionBar()
    }

    private fun setUpActionBar() {
        //set title to activity
        supportActionBar!!.title = "Dashboard"

        tvMainEmpName.text = loginResponse!!.data!!.name
        rbMainEmp.rating = loginResponse!!.userData!!.averageRating!!.toFloat()
        tvMainAvgReview.text = loginResponse!!.userData!!.performanceStatus

        rvEmployerList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        //set adapter for recycler view
        rvEmployerList.adapter = EmployerListAdapter(loginResponse!!.userData!!.data) { result ->
            //call new activity to show details and pass uid to particular activity
            val intent = Intent(this@MainActivity, ReviewDetailsActivity::class.java)
            intent.putExtra(getString(R.string.employer_id), result.id)
            startActivity(intent)
        }
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
