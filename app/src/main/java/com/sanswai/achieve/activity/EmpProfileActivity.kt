package com.sanswai.achieve.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.MenuItem
import android.view.View
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.fragment.*
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.layout_profile.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class EmpProfileActivity : BaseActivity(), VolleyService.SetResponse {

    var tabLayout: TabLayout? = null
    var employee_id: String? = null
    var preferences: Preferences? = null
    var services: VolleyService? = null
    var arrFragment = ArrayList<Fragment>()
    lateinit var collapsingToolbarLayout :CollapsingToolbarLayout

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_profile)
        setSupportActionBar(toolbarMain)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        services = VolleyService(this)
        preferences = Preferences.getInstance(this)
        if (preferences?.getPreferencesString(getString(R.string.user_type)) == "employee") {
            employee_id = preferences!!.getPreferencesInt(getString(R.string.user_id), 0).toString()
        } else {
            employee_id = intent.getIntExtra(getString(R.string.employee_id), 0).toString()
        }
        getEmployeeDetails()

    }

    private fun setUpTabsView() {
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), "Extra Image")
        collapsingToolbarLayout = findViewById(R.id.collapsingToolBar_hotel_details)
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(PersonalDetailsFragment())
        adapter.addFragment(KeySkillFragment())
        adapter.addFragment(DesiredCareerProfileFrag())
        adapter.addFragment(ResumeHeadlineFragment())
        adapter.addFragment(ProjectsFragment())
        adapter.addFragment(EmploymentFragment())
        adapter.addFragment(EducationFragment())

        arrFragment.add(PersonalDetailsFragment())
        arrFragment.add(KeySkillFragment())
        arrFragment.add(DesiredCareerProfileFrag())
        arrFragment.add(ResumeHeadlineFragment())
        arrFragment.add(ProjectsFragment())
        arrFragment.add(EmploymentFragment())
        arrFragment.add(EducationFragment())



        viewPager!!.adapter = adapter
        tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.getTabAt(0)!!.text = "Personal Details"
        tabLayout!!.getTabAt(1)!!.text = "Key Skills"
        tabLayout!!.getTabAt(2)!!.text = "Desired Career Profile"
        tabLayout!!.getTabAt(3)!!.text = "Resume Headline"
        tabLayout!!.getTabAt(4)!!.text = "Projects"
        tabLayout!!.getTabAt(5)!!.text = "Employment"
        tabLayout!!.getTabAt(6)!!.text = "Education"
    }

    private fun getEmployeeDetails() {
        services?.callJsonGETRequest(getString(R.string.api_employee_details) + employee_id, JSONObject())
        services?.mResponseInterface = this
    }

    @SuppressLint("RestrictedApi")
    override fun onSuccess(methodName: String, response: Any) {
        println("response is $response")
        preferences?.setPreferencesBody(getString(R.string.pref_employee_details), response.toString())
        val jsonResponse = preferences?.getPreferencesString(getString(R.string.pref_employee_details))
        println("project $jsonResponse")
        var responseObject: EmployeeDetails? = null
        try {
            responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (responseObject != null) {
            if (preferences?.getPreferencesString(getString(R.string.user_type)) == "employee") {
                fabPersonalDetails.visibility = View.VISIBLE
            } else {
                fabPersonalDetails.visibility = View.GONE
            }
            setUpTabsView()
            if ((responseObject).users?.data?.profilePic != null) {
                Picasso.get().load((responseObject).users?.data?.profilePic).centerInside().resize(200, 200)
                        .onlyScaleDown().error(getDrawable(R.mipmap.ic_launcher)).placeholder(getDrawable(R.mipmap.ic_launcher)).into(ivProfile)
            }
            tvTelephone.text = responseObject.users?.data?.mobileNumber
            tvEmail.text = responseObject.users?.data?.email
            collapsingToolbarLayout.title = responseObject.users?.data?.name
        }
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
        println("error  is " + volleyError.networkResponse)
    }

    fun onFabCkick(){
        if (arrFragment.get(viewPager.currentItem) is PersonalDetailsFragment) {
            showToast("clciked personal fragmentsjkfdjkdfjkldfjfdskjadsfbkaldsbfkjasdfbadskfbdsjlf jasf jfbdfbadfh jadfbasdkfgl")
        }
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        val mFragmentList = ArrayList<Fragment>()
        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment) {
            mFragmentList.add(fragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
