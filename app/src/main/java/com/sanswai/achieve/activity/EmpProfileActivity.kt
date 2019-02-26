package com.sanswai.achieve.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewCompat
import android.view.MenuItem
import android.view.View
import com.sanswai.achieve.R
import com.sanswai.achieve.fragment.*
import com.sanswai.achieve.global.BaseActivity
import kotlinx.android.synthetic.main.activity_emp_profile.*
import java.util.*

class EmpProfileActivity : BaseActivity() {

    var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_profile)
        setSupportActionBar(toolbarMain)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), "Extra Image")
        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolBar_hotel_details)
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
        collapsingToolbarLayout.title = "Kiran Shetti"
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(PersonalDetailsFragment())
        adapter.addFragment(ResumeHeadlineFragment())
        adapter.addFragment(ProjectsFragment())
        adapter.addFragment(EmploymentFragment())
        adapter.addFragment(EducationFragment())
        viewPager!!.adapter = adapter
        tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.getTabAt(0)!!.text = "Personal Details"
        tabLayout!!.getTabAt(1)!!.text = "Resume Headline"
        tabLayout!!.getTabAt(2)!!.text = "Projects"
        tabLayout!!.getTabAt(3)!!.text = "Employment"
        tabLayout!!.getTabAt(4)!!.text = "Education"
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