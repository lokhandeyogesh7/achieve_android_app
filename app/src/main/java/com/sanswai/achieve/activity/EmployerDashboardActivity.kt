package com.sanswai.achieve.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.sanswai.achieve.R
import com.sanswai.achieve.adapter.EmployeeDashboardAdapter
import com.sanswai.achieve.model.Employee
import kotlinx.android.synthetic.main.activity_employer_dashbaord.*

class EmployerDashboardActivity : AppCompatActivity() {

    var employeeList: ArrayList<Employee>? = null
    var adapter: EmployeeDashboardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employer_dashbaord)

        setupActionBar()

        employeeList = ArrayList()
        adapter = EmployeeDashboardAdapter(this@EmployerDashboardActivity, employeeList)

        val mLayoutManager = LinearLayoutManager(this@EmployerDashboardActivity, LinearLayoutManager.VERTICAL, false)
        rvEmployeeList.layoutManager = mLayoutManager
        rvEmployeeList.itemAnimator = DefaultItemAnimator()
        rvEmployeeList.adapter = adapter

        prepareEmployeeList()
    }

    private fun prepareEmployeeList() {

        for (i in 0 until 5) {
            var projects= Employee("Kiran Shetti","${i}@mymail.com","9876543210","Excellent")
            employeeList!!.add(projects)
        }

        adapter!!.notifyDataSetChanged()

    }

    private fun setupActionBar() {
        //set title to the activity
        supportActionBar!!.title = "Dashboard"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //action to only action menu button
        when(item!!.itemId) {
            R.id.logout->{
                Toast.makeText(this, "You are successfully logged out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@EmployerDashboardActivity, LoginActivity::class.java))
                finish()
                return true
            }
            else-> {
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
